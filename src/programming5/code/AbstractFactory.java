/*
 * AbstractFactory.java
 *
 * Copyright 2016 Andres Quiroz Hernandez
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

package programming5.code;

import programming5.collections.MapBuilder;
import programming5.strings.StringOperations;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * <p>Utility class that allows instantiating and initializing classes using conventions for declaring properties for the
 * class and its instances. Properties can be declared directly with a {@link Properties} map or in a properties file
 * that is accessible to the application. These factory methods also wrap any exceptions thrown during class instantiation
 * into RuntimeException objects, which makes use of this class friendlier to prototyping and debugging with less
 * code clutter from try/catch constructs.</p>
 * <p>Property conventions are as follows:</p>
 * <p> - "class" key is reserved for declaring the fully qualified class name of the class to be instantiated, which is
 * expected to have a parameterless constructor.</p>
 * <p> - Class members that provide standard setter methods will be invoked after instantiation with values provided in
 * the properties. For example, if "color" is a property key, then the class is expected to provide a setColor() setter
 * method. Note that the first letter of the key is converted to uppercase in the setter, consistent with Java conventions.
 * The AbstractFactory methods can be used to set String, integer, double floating point, and boolean properties, but
 * property values must be given as strings in the properties map.</p>
 * <p> - A single properties file can be used to declare properties for multiple classes, for example, multiple
 * implementations of a single interface. Each class declared in a properties file is referred to as a type, and, when
 * containing multiple types, a properties file must declare a "default_type" key with an identifier for that type. The
 * property keys for the corresponding type must then be prefixed by that identifier. For example, the following
 * properties file declares two classes for two different shape objects that have a color property, and the circle
 * will be instantiated by default:</p>
 * <ul>
 *     <li>default_type=circle</li>
 *     <li>circle.class=com.example.shapes.Circle</li>
 *     <li>circle.color=blue</li>
 *     <li>square.class=com.example.shapes.Square</li>
 *     <li>square.color=red</li>
 * </ul>
 * <p>To instantiate the square in the example above, methods are provided by AbstractFactory to override the
 * "default_type" property.</p>
 * <p> - Classes may implement the {@link Initializable} interface or simply declare an init() method, which will be
 * invoked by AbstractFactory methods after instantiation and after all setters have been called with the given property
 * values.</p>
 */
// TODO: Define conventions for invoking constructors with parameters, to avoid the need for the Initializable convention
public abstract class AbstractFactory {

    public static final String DEFAULT_TYPE_KEY = "default_type";
    public static final String CLASS_KEY = "class";

    private static Map<Class, Function<String, Object>> setterTypeAdapters = MapBuilder.in(new HashMap<Class, Function<String, Object>>())
            .put(String.class, s -> s)
            .put(int.class, Integer::parseInt)
            .put(double.class, Double::parseDouble)
            .put(boolean.class, Boolean::parseBoolean)
            .get();

    /**
     * Creates a class instance with the given properties map
     * @param classProperties the properties of the class, which must contain at least a "class" property
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static Object createFromProperties(Properties classProperties) {
        try {
            final String propertyPrefix = classProperties.containsKey(DEFAULT_TYPE_KEY) ?
                    classProperties.getProperty(DEFAULT_TYPE_KEY) + "."
                    : "";
            final Class typeClass = Class.forName(classProperties.getProperty(propertyPrefix + CLASS_KEY));
            final Object instance = typeClass.newInstance();
            classProperties.stringPropertyNames().stream()
                    .filter(key -> key.startsWith(propertyPrefix) && !key.equals(propertyPrefix + CLASS_KEY))
                    .map(key -> propertyPrefix.isEmpty() ? key : key.substring(1 + key.lastIndexOf(".")))
                    .forEach(key -> {
                        String value = classProperties.getProperty(propertyPrefix + key);
                        if (!value.contains(";")) { // TODO: Define escape mechanism for semicolon
                            try {
                                String setterName = "set" + StringOperations.capitalize(key);
                                Method setter = setterTypeAdapters.keySet().stream()
                                        .map(parameterType -> {
                                            try {
                                                return typeClass.getMethod(setterName, parameterType);
                                            }
                                            catch (NoSuchMethodException e) {
                                                return null;
                                            }
                                        })
                                        .filter(method -> method != null)
                                        .findFirst().get();
                                setter.invoke(instance, adaptType(value, setter));
                            }
                            catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                        else {
                            // TODO: Invoke adders
                        }
                    });

            if (instance instanceof Initializable) {
                ((Initializable) instance).init();
            }
            else {
                try {
                    typeClass.getMethod("init").invoke(instance);
                }
                catch (NoSuchMethodException nsm) {/* Don't have to do anything */}
            }
            return instance;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a class instance of the given type with the given properties map
     * @param classProperties the properties of the class, which must contain at least a "class" property
     * @param typeClass the expected class of the instance to be returned
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static <T> T createFromPropertiesWithClass(Properties classProperties, Class<T> typeClass) {
        return (T) createFromProperties(classProperties);
    }

    /**
     * Creates a class instance with the properties declared in the given properties file
     * @param propertiesFile path to the properties file, which contain at least a "class" property, and, if multiple
     *                       classes are declared, a "default_type" property (see key naming conventions in the general
     *                       class documentation)
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static Object createFromProperties(String propertiesFile) {
        Properties classProperties = new Properties();
        try {
            classProperties.load(new FileInputStream(propertiesFile));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return createFromProperties(classProperties);
    }

    /**
     * Creates a class instance of the given type with the properties declared in the given properties file
     * @param propertiesFile path to the properties file, which may omit the "class" property, as long as multiple
     *                       classes are not declared, in which case a "default_type" property and a prefixed "class"
     *                       property must be included (see key naming conventions in the general class documentation)
     * @param typeClass the expected class of the instance to be returned
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static <T> T createFromPropertiesWithClass(String propertiesFile, Class<T> typeClass) {
        return (T) createFromPropertiesWithDefaults(propertiesFile, Collections.singletonMap("class", typeClass.getName()));
    }

    /**
     * Creates a class instance with the properties declared in the given properties file, using the properties from
     * the given map to override any properties from the file with the same keys. Note that this method should not be
     * used to provide default values for properties, because they will override any properties declared in the file;
     * instead, the {@link #createFromPropertiesWithDefaults(String, Map)} method should be used.
     * @param propertiesFile path to the properties file, which contain at least a "class" property, and, if multiple
     *                       classes are declared, a "default_type" property (see key naming conventions in the general
     *                       class documentation)
     * @param overrideProperties map with properties to override those specified in the properties file (e.g. overriding
     *                           the "default_type" property)
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static Object createFromProperties(String propertiesFile, Map<String, String> overrideProperties) {
        Properties classProperties = new Properties();
        try {
            classProperties.load(new FileInputStream(propertiesFile));
            classProperties.putAll(overrideProperties);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return createFromProperties(classProperties);
    }

    /**
     * Convenience method provided for the common pattern of overriding the "default_type" property of the given
     * properties file
     * @param propertiesFile path to the properties file, which contain at least a "class" property, and, if multiple
     *                       classes are declared, a "default_type" property (see key naming conventions in the general
     *                       class documentation)
     * @param type type identifier, which must correspond to the prefix used for some type declared in the properties
     *             file (e.g. "square" for the example in the general docs)
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static Object createFromPropertiesForType(String propertiesFile, String type) {
        return createFromProperties(propertiesFile, MapBuilder.start(DEFAULT_TYPE_KEY, type).get());
    }

    /**
     * Creates a class instance with the properties declared in the given properties file, using the properties in the
     * given map to provide default values for properties that may not be declared in the file. Note that this method
     * cannot be used to override properties that are declared in the file (e.g. to override the "default_type" property);
     * the {@link #createFromProperties(String, Map)} or {@link #createFromPropertiesForType(String, String)} methods
     * should be used for that purpose.
     * @param propertiesFile path to the properties file, which contain at least a "class" property, and, if multiple
     *                       classes are declared, a "default_type" property (see key naming conventions in the general
     *                       class documentation)
     * @param defaultProperties map with properties that will be used if the corresponding key is not declared in the
     *                          properties file
     * @return the configured and initialized class instance
     * @throws RuntimeException wrapping any exceptions thrown during instantiation, configuration, or initialization
     */
    public static Object createFromPropertiesWithDefaults(String propertiesFile, Map<String, String> defaultProperties) {
        Properties classProperties = new Properties();
        classProperties.putAll(defaultProperties);
        try {
            classProperties.load(new FileInputStream(propertiesFile));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return createFromProperties(classProperties);
    }

    private static boolean isInitializable(Object instance, Class typeClass) {
        try {
            return (instance instanceof Initializable || typeClass.getMethod("init") != null);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static Object adaptType(String value, Method method) {
        return setterTypeAdapters.get(method.getParameterTypes()[0]).apply(value);
    }
}
