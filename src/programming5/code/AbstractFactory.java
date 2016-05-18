package programming5.code;

import programming5.strings.StringOperations;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by andres on 5/18/16.
 */
public abstract class AbstractFactory {

    public static final String DEFAULT_TYPE_KEY = "default_type";
    public static final String CLASS_KEY = "class";
    public static final String DO_INIT_KEY = "do_init";

    private static List<Class> setterTypes = Arrays.asList(String.class, int.class, double.class, boolean.class);

//    public static Object createFromProperties(Map<String, Object> classProperties) {
//
//    }

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
                    .filter(key -> key.startsWith(propertyPrefix)
                            && !key.equals(propertyPrefix + CLASS_KEY)
                            && !key.equals(propertyPrefix + DO_INIT_KEY)
                    )
                    .map(key -> propertyPrefix.isEmpty() ? key : key.substring(1 + key.lastIndexOf(".")))
                    .forEach(key -> {
                        String value = classProperties.getProperty(propertyPrefix + key);
                        if (!value.contains(";")) { // TODO: Define escape mechanism for semicolon
                            try {
                                String setterName = "set" + StringOperations.capitalize(key);
                                Method setter = setterTypes.stream()
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
                                setter.invoke(instance, value);
                            }
                            catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                        else {
                            // TODO: Invoke adders
                        }
                    });

            if (Boolean.parseBoolean(classProperties.getProperty(propertyPrefix + DO_INIT_KEY, "true"))) {
                if (instance instanceof Initializable) {
                    ((Initializable) instance).init();
                }
                else {
                    try {
                        typeClass.getMethod("init").invoke(instance);
                    }
                    catch (NoSuchMethodException nsm) {/* Don't have to do anything */}
                }
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
}
