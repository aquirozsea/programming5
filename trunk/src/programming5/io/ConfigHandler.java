/*
 * ConfigHandler.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

package programming5.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Class that automatically loads and manages properties set through one or more properties files, specified on the java command line using
 * a -Dconf=filepath{;filepath} switch entry. Property files can also be loaded after initial launch using the 
 * loadPropertyFile method. This is a convenience class based on the system properties object. Note that this class is non-
 * intrusive, meaning that exceptions will not be thrown by inexistent/bad property files. Instead, the problems will be
 * logged using an available logger.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public abstract class ConfigHandler {

    static final Properties systemProperties;

    static {
        systemProperties = System.getProperties();
        String[] confFileLocations = systemProperties.getProperty("conf", "").split(";");
        for (String confFileLocation : confFileLocations) {
            if (!confFileLocation.isEmpty()) {
                loadPropertyFile(confFileLocation);
            }
        }
    }

    /**
     * Loads the property file in the given path, or warns if the file does not exist or is not valid, but does not throw an exception.
     * Newly loaded and previous properties are all then accessible via the getProperty methods.
     */
    public static void loadPropertyFile(String filePath) {
        if (systemProperties != null) {
            try {
                systemProperties.load(new InputStreamReader(new FileInputStream(filePath)));
            }
            catch (FileNotFoundException fnfe) {
                LogUtil.warn("Configuration file " + filePath + " not found");
            }
            catch (IOException ioe) {
                LogUtil.warn("Bad configuration file (" + filePath + "): " + ioe.getMessage());
            }
        }
    }

    /**
     * @return the property associated with the given name, from System.getProperties or one of the previously loaded properties files.
     * @throws IllegalArgumentException if a property with the given name has not been set.
     */
    public static String getProperty(String propertyName) {
        String ret = null;
        if (systemProperties != null) {
            ret = systemProperties.getProperty(propertyName);
        }
        if (ret == null) {
            throw new IllegalArgumentException("ScIn Configuration: Could not get property: Property named " + propertyName + " not set");
        }
        return ret;
    }

    /**
     * @return the property associated with the given name, from System.getProperties or one of the previously loaded properties files, if it has been set; otherwise, the given default value
     */
    public static String getProperty(String propertyName, String defaultValue) {
        String ret = null;
        if (systemProperties != null) {
            ret = systemProperties.getProperty(propertyName);
        }
        if (ret == null) {
            ret = defaultValue;
        }
        return ret;
    }

    /**
     * Allows setting values for properties that have not been set by a property file managed by this object. If the property 
     * has already been set by a loaded file, the method call has no effect. If a file with the property is loaded after
     * this method is called, subsequent calls to getProperty will return the loaded property and not the one set by this
     * method.
     */
    public static void setDefaultProperty(String key, String value) {
        if (systemProperties != null) {
            String current = systemProperties.getProperty(key);
            if (current == null) {
                systemProperties.setProperty(key, value);
            }
        }
    }

}
