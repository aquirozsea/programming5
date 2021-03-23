/*
 * JdbcDriver.java
 *
 * Copyright 2011 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.io.jdbc;

import programming5.arrays.ArrayOperations;
import programming5.collections.HashTable;
import programming5.collections.NotFoundException;
import programming5.io.Debug;
import programming5.strings.KeyValuePairMatcher;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class is a utility wrapper for a JDBC database connection, providing basic CRUD methods
 * TODO: Illegal state exceptions, what is the best way to provide access to db instance?
 * @version 6.2
 */
public class JdbcDriver {

    public enum DBType {

        mysql ("jdbc:mysql", "com.mysql.jdbc.Driver", 3306),
        postgresql ("jdbc:postgresql", "org.postgresql.Driver", 5432);

        private final String connectSchema;
        private final String myDriver;
        private final int defaultPort;

        DBType(String refSchema, String refDriver, int refPort) {
            connectSchema = refSchema;
            myDriver = refDriver;
            defaultPort = refPort;
        }

        public String getConnectSchema() {
            return connectSchema;
        }

        public String getDriver() {
            return myDriver;
        }

        public int getDefaultPort() {
            return defaultPort;
        }

        public String getDefaultPortString() {
            return Integer.toString(defaultPort);
        }

    };

    public static enum DBPropertyKey {user, password, host, port, driver};

    Connection db = null;
    Map<String, Savepoint> savepoints = new HashMap<String, Savepoint>();

    /**
     * Starts a driver instance making a connection to the specified database. It is assumed that no username/password are required for 
     * the connection.
     * This constructor requires the DB driver to be registered externally beforehand
     * @param dbURL a URL of the form "<connect_schema>://<host>:<port>/<dbname>"
     */
    public JdbcDriver(String dbURL) throws SQLException {
        db = DriverManager.getConnection(dbURL);
    }

    /**
     * Starts a driver instance making a connection to the specified database, with the username/password provided
     * This constructor requires the DB driver to be registered externally beforehand
     * @param dbURL a URL of the form "<connect_schema>://<host>:<port>/<dbname>"
     */
    public JdbcDriver(String dbURL, String user, String password) throws SQLException {
        db = DriverManager.getConnection(dbURL, user, password);
    }

    /**
     * Starts a driver instance making a connection to the specified database. Username/password required for 
     * the connection and other properties may be specified in the connect properties parameter, using keys in DBPropertyKey enumeration 
     * provided by the class.
     * The DB driver must either be registered externally beforehand, or a driver class name must be provided in the properties object
     * using DBPropertyKey.driver
     * @param dbURL a URL of the form "<connect_schema>://<host>:<port>/<dbname>"
     * @param connectProperties properties object with key strings given by DBPropertyKey enumeration 
     */
    public JdbcDriver(String dbURL, Properties connectProperties) throws SQLException {
        String driverClass = connectProperties.getProperty(DBPropertyKey.driver.toString());
        if (driverClass != null) {
            try {
                Class.forName(driverClass);
            }
            catch (Exception e){
                throw new SQLException("JdbcDriver: Could not register database driver: " + e.getMessage(), e);
            }
        }
        db = DriverManager.getConnection(dbURL, connectProperties);
    }

    /**
     * Simplified constructor that encapsulates commonly used drivers for particular database types. Uses an inner enumeration of database 
     * types, and the known connect schema and drivers for each. The constructor will register the driver and create the connection.
     * Username/password required for the connection and other properties may be specified in the connect properties parameter, using 
     * keys in DBPropertyKey enumeration provided by the class. 
     * @param dbType type from the inner enumeration DBType
     * @param databaseName database name
     * @param connectProperties zero or more properties that may be required for creating the connection, given as key:value pairs (e.g. "user:admin")
     */
    public JdbcDriver(DBType dbType, String databaseName, String... connectProperties) throws SQLException {
        try {
            Class.forName(dbType.getDriver());
        }
        catch (Exception e){
            throw new SQLException("JdbcDriver: Could not register database driver: " + e.getMessage(), e);
        }
        String host = fillParameter(DBPropertyKey.host, connectProperties, "localhost");
        String port = fillParameter(DBPropertyKey.port, connectProperties, dbType.getDefaultPortString());
        // Create connect URL with given or default values
        String dbURL = dbType.getConnectSchema() + "://" + host + ":" + port + "/" + databaseName;
        // Fill user and password, if given
        String user = fillParameter(DBPropertyKey.user, connectProperties, null);
        String password = fillParameter(DBPropertyKey.password, connectProperties, null);
        if (user != null) {
            Properties propertiesObject = new Properties();
            propertiesObject.put(DBPropertyKey.user.toString(), user);
            if (password != null) {
                propertiesObject.put(DBPropertyKey.password.toString(), password);
            }
            db = DriverManager.getConnection(dbURL, propertiesObject);
        }
        else {
            db = DriverManager.getConnection(dbURL);
        }
    }

    /**
     * Simplified constructor that encapsulates commonly used drivers for particular database types. Uses an inner enumeration of database
     * types, and the known connect schema and drivers for each. The constructor will register the driver and create the connection.
     * Username/password required for the connection and other properties may be specified in the connect properties parameter, using
     * keys in DBPropertyKey enumeration provided by the class.
     * @param dbType type from the inner enumeration DBType
     * @param databaseName database name
     * @param connectProperties zero or more properties that may be required for creating the connection, given as key, value pairs in the map
     */
    public JdbcDriver(DBType dbType, String databaseName, Map<String, String> connectProperties) throws SQLException {
        try {
            Class.forName(dbType.getDriver());
        }
        catch (Exception e){
            throw new SQLException("JdbcDriver: Could not register database driver: " + e.getMessage(), e);
        }
        HashTable<String, String> defaultableProperties = new HashTable<String, String>(connectProperties);
        String host = defaultableProperties.safeGet(DBPropertyKey.host.toString(), "localhost");
        String port = defaultableProperties.safeGet(DBPropertyKey.port.toString(), dbType.getDefaultPortString());
        // Create connect URL with given or default values
        String dbURL = dbType.getConnectSchema() + "://" + host + ":" + port + "/" + databaseName;
        // Fill user and password, if given
        String user = defaultableProperties.safeGet(DBPropertyKey.user.toString(), null);
        String password = defaultableProperties.safeGet(DBPropertyKey.password.toString(), null);
        if (user != null) {
            Properties propertiesObject = new Properties();
            propertiesObject.put(DBPropertyKey.user.toString(), user);
            if (password != null) {
                propertiesObject.put(DBPropertyKey.password.toString(), password);
            }
            db = DriverManager.getConnection(dbURL, propertiesObject);
        }
        else {
            db = DriverManager.getConnection(dbURL);
        }
    }

    public Connection getConection() {
        return db;
    }

    public void createTable(String name, String[] fields, String[] types) throws SQLException {
        Statement sqlStmt = db.createStatement();
        String sqlCode = "CREATE TABLE " + name;
        if (fields.length > 0 && types.length == fields.length) {
            sqlCode += "(" + fields[0] + " " + types[0];
            for (int i = 1; i < fields.length; i++) {
                sqlCode += ", " + fields[i] + " " + types[i];
            }
            sqlCode += ");";
        }
        else {
            throw new SQLException("JdbcTest: Could not create table: Field and type error");
        }
        sqlStmt.execute(sqlCode);
    }

    public void insertRecord(String table, String[] columns, String[] values) throws SQLException {
        if (values.length > 0) {
            Statement sqlStmt = db.createStatement();
            String sqlCode = "INSERT INTO " + table + " ";
            if (columns != null) {
                if (columns.length > 0) {
                    sqlCode += "(" + columns[0];
                    for (int i = 1; i < columns.length; i++) {
                        sqlCode += ", " + columns[i];
                    }
                    sqlCode += ") ";
                }
                else {
                    throw new SQLException("JdbcTest: Could not insert: Field error");
                }
            }
            if (values.length > 0) {
                if (columns != null) {
                    if (values.length != columns.length) {
                        throw new SQLException("JdbcTest: Could not create table: Field and type error");
                    }
                }
                sqlCode += "VALUES (" + values[0];
                for (int i = 1; i < values.length; i++) {
                    sqlCode += ", " + values[i];
                }
                sqlCode += ");";
            }
            Debug.println(sqlCode);
            sqlStmt.execute(sqlCode);
        }
    }

    public ResultSet query(String[] fields, String[] tables, String... condition) throws SQLException {
        Statement sqlStmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sqlCode = "SELECT ";
        if (fields != null) {
            if (fields.length > 0) {
                sqlCode = append(fields, sqlCode);
            }
            else {
                sqlCode += "*";
            }
        }
        else {
            sqlCode += "*";
        }
        sqlCode += " FROM ";
        try {
            sqlCode = append(tables, sqlCode);
        }
        catch (IllegalArgumentException iae) {
            throw new SQLException("Cannot execute query: Must specify at least one table");
        }
        if (condition != null) {
            if (condition.length > 0) {
                for (int i = 0; i < condition.length; i++) {
                    sqlCode += " " + condition[i];
                }
            }
        }
        sqlCode += ";";
        Debug.println(sqlCode);
        return sqlStmt.executeQuery(sqlCode);
    }

    public int update(String table, String field, String value, String... condition) throws SQLException {
        Statement sqlStmt = db.createStatement();
        String sqlCode = "UPDATE " + table + " SET " + field + " = " + value;
        for (int i = 0; i < condition.length; i++) {
            sqlCode += " " + condition;
        }
        sqlCode += ";";
        return sqlStmt.executeUpdate(sqlCode);
    }

    public DatabaseMetaData getConnectionMetadata() throws SQLException {
        return db.getMetaData();
    }

    public void startTransaction() throws SQLException {
        db.setAutoCommit(false);
    }

    public void setSavepoint(String name) throws SQLException {
        Savepoint sv = db.setSavepoint(name);
        savepoints.put(name, sv);
    }

    public void rollback() throws SQLException {
        db.rollback();
    }

    public void rollback(String savepointName) throws SQLException {
        Savepoint sv = savepoints.get(savepointName);
        if (sv != null) {
            db.rollback(sv);
        }
        else {
            throw new SQLException("Cannot rollback to savepoint: Savepoint not valid");
        }
    }

    public void endTransaction() throws SQLException {
        db.commit();
        db.setAutoCommit(true);
    }

    public void closeConnection() throws SQLException {
        db.close();
    }

    public static void main(String[] args) {
        try {
            Debug.enableDefault();
            JdbcDriver test = new JdbcDriver("jdbc:odbc:COFFEE");
//            test.createTable("Varieties", new String[] {"Cof_name", "Sup_ID", "Price", "Sales", "Total"},
//                                          new String[] {"Text", "Number", "Number", "Number", "Number"});
//            test.insertRecord("Varieties", new String[] {"Cof_name", "Sup_ID", "Price", "Sales", "Total"},
//                                           new String[] {"'Colombian'", "101", "7.99", "0", "0"});
//            test.insertRecord("Varieties", new String[] {"Cof_name", "Sup_ID", "Price", "Sales", "Total"},
//                                           new String[] {"'French_Roast'", "49", "8.99", "0", "0"});
//            test.insertRecord("Varieties", new String[] {"Cof_name", "Sup_ID", "Price", "Sales", "Total"},
//                                           new String[] {"'Espresso'", "150", "9.99", "0", "0"});
//            test.insertRecord("Varieties", new String[] {"Cof_name", "Sup_ID", "Price", "Sales", "Total"},
//                                           new String[] {"'Colombian_decaf'", "101", "8.99", "0", "0"});
//            test.insertRecord("Varieties", new String[] {"Cof_name", "Sup_ID", "Price", "Sales", "Total"},
//                                           new String[] {"'French_Roast_decaf'", "49", "9.99", "0", "0"});
            ResultSet result = test.query(new String[] {"Cof_name", "Price"}, new String[] {"Varieties"}, "WHERE Price < 8");
            for (result.first(); !result.isAfterLast(); result.next()) {
                System.out.println(result.getString("Cof_name") + ": " + result.getString("Price"));
            }
            try {
                test.startTransaction();
                System.out.println(test.update("Varieties", "Price", "8.99", "WHERE Cof_name = 'Colombian'"));
                test.endTransaction();
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
                System.out.println("Rolling back");
                test.rollback();
            }
            result = test.query(new String[] {"Cof_name", "Price"}, new String[] {"Varieties"}, "WHERE Price < 8");
            for (result.first(); !result.isAfterLast(); result.next()) {
                System.out.println(result.getString("Cof_name") + ": " + result.getString("Price"));
            }
//            while (result.next()) {
            test.closeConnection();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    private static String append(String[] list, String line) {
        if (list != null) {
            if (list.length > 0) {
                line += list[0];
                for (int i = 1; i < list.length; i++) {
                    line += ", " + list[i];
                }
            }
            else {
                throw new IllegalArgumentException("Empty list");
            }
        }
        else {
            throw new IllegalArgumentException("Null list");
        }
        return line;
    }

    private String fillParameter(DBPropertyKey key, String[] properties, String defaultValue) {
        String ret;
        try {
            int pos = ArrayOperations.findInSequence(key.toString(), properties, new KeyValuePairMatcher());
            ret = properties[pos].split(":")[1].trim();
        }
        catch (NotFoundException nfe) {
            ret = defaultValue;
        }
        return ret;
    }

}
