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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import programming5.io.Debug;

/**
 *
 * @version 6.19
 */
public class JdbcDriver {

    Connection db = null;
    Map<String, Savepoint> savepoints = new HashMap<String, Savepoint>();

//    static {
//        try {
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//        }
//        catch (ClassNotFoundException cnfe) {
//            cnfe.printStackTrace();
//        }
//    }

    public JdbcDriver(String dbURL) throws SQLException {
        db = DriverManager.getConnection(dbURL);
    }

    public JdbcDriver(String dbURL, Properties connectProperties) throws SQLException {
        db = DriverManager.getConnection(dbURL, connectProperties);
    }
    
    public JdbcDriver(String dbURL, String user, String password) throws SQLException {
        db = DriverManager.getConnection(dbURL, user, password);
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

}
