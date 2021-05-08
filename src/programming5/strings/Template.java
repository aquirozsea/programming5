package programming5.strings;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Allows labeled string formatting. A template wraps a string with <placeholders> that can be substituted
 * given the placeholder name, or a name:value pair string.
 */
public class Template {

    private final String template;

    public Template(String template) {
        this.template = template;
    }

    /**
     * Record that defines a substitution
     */
    static record Sub(String name, Object sub) {

        /**
         * @param subString a string of the form name:value
         * @return the substitution given by new Sub(name, value)
         */
        static Sub parse(String subString) {
            String[] parts = subString.split("\\s*:\\s*");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Expected name:string, but found %s".formatted(subString));
            }
            return new Sub(parts[0], parts[1]);
        }

    }

    /**
     * Applies the given substitutions to the given template string
     * @param template a template string
     * @param subs the substitutions
     * @return the formatted string
     */
    public static String format(String template, Sub... subs) {
        return new Template(template).realize(subs);
    }

    /**
     * Applies the given substitutions of name:value to the given template string
     * @param template a template string
     * @param subs the substitutions as name:value strings
     * @return the formatted string
     */
    public static String format(String template, String... subs) {
        return format(template,
                Stream.of(subs).map(Sub::parse).toArray(Sub[]::new));
    }

    /**
     * Applies the given substitutions to the given template string
     * @param template a template string
     * @param subs the substitutions as a map of String, Object
     * @return the formatted string
     */
    public static String format(String template, Map<String, Object> subs) {
        return format(template, subs.entrySet().stream()
                .map(entry -> new Sub(entry.getKey(), entry.getValue()))
                .toArray(Sub[]::new));
    }

    /**
     * @param name the name to substitute
     * @param sub the object to substitute in place of the name
     * @return a new template where <name> was filled by sub.toString()
     */
    public Template fill(String name, Object sub) {
        return new Template(apply(new Sub(name, sub)));
    }

    /**
     * Applies all of the given substitutions to this template
     * @param subs the substitutions to apply
     * @return the realized template
     */
    public String realize(Sub... subs) {
        return Stream.of(subs)
                .reduce(this, (t, sub) -> t.fill(sub.name, sub.sub), (t1, t2) -> t1)
                .toString();
    }

    @Override
    public String toString() {
        return template;
    }

    private static String quote(String string) {
        return "<" + string + ">";
    }

    private String apply(Sub sub) {
        return template.replaceAll(quote(sub.name), sub.sub.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template1 = (Template) o;
        return template.equals(template1.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(template);
    }
}
