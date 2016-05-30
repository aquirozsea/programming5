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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * Created by andres on 5/18/16.
 * TODO: Refactor common code out of different method variations
 * TODO: Add license text
 */
public abstract class AbstractFactory {

    public static final String DEFAULT_TYPE_KEY = "default_type";
    public static final String CLASS_KEY = "class";
//    public static final String DO_INIT_KEY = "do_init";

    private static Map<Class, Function<String, Object>> setterTypeAdapters = MapBuilder.in(new HashMap<Class, Function<String, Object>>())
            .put(String.class, s -> s)
            .put(int.class, Integer::parseInt)
            .put(double.class, Double::parseDouble)
            .put(boolean.class, Boolean::parseBoolean)
            .get();

    public static <T> T createFromPropertiesWithClass(String propertiesFile, Class<T> typeClass) {
        try {
            Properties classProperties = new Properties();
            classProperties.load(new FileInputStream(propertiesFile));
            final String propertyPrefix = classProperties.containsKey(DEFAULT_TYPE_KEY) ?
                    classProperties.getProperty(DEFAULT_TYPE_KEY) + "."
                    : "";
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
            return (T) instance;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createFromProperties(String propertiesFile) {
        try {
            Properties classProperties = new Properties();
            classProperties.load(new FileInputStream(propertiesFile));
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

    public static Object createFromProperties(String propertiesFile, Map<String, String> overrideProperties) {
        try {
            Properties classProperties = new Properties();
            classProperties.load(new FileInputStream(propertiesFile));
            classProperties.putAll(overrideProperties);
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
