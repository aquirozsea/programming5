package programming5.strings;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TemplateTest {

    @Test
    public void testFill() {
        Template template = new Template("Fill <pronoun>");
        assertEquals(new Template("Fill me"), template.fill("pronoun", "me"));
    }

    @Test
    public void testFillMultiple() {
        Template template = new Template("I, <name>, do solemnly swear that my name is <name>");
        assertEquals("I, Andres, do solemnly swear that my name is Andres",
                template.fill("name", "Andres").toString());
    }

    @Test
    public void testFluent() {
        Template filled = new Template("Written by <name> on <date>")
                .fill("name", "me")
                .fill("date", "today");
        assertEquals("Written by me on today", filled.toString());
    }

    @Test
    public void testAutoString() {
        assertEquals("Written by me on today", String.format("%s",
                new Template("Written by <name> on <date>")
                        .fill("name", "me")
                        .fill("date", "today")));
    }

    @Test
    public void testRealizeEmpty() {
        Template template = new Template("I will be <unrealized>");
        assertEquals(template.toString(), template.realize());
    }

    @Test
    public void testRealizeSingle() {
        Template template = new Template("Your name <here>");
        assertEquals("Your name Your name", template.realize(
                new Template.Sub("here", "Your name")));
    }

    @Test
    public void testRealizeMultiple() {
        Template template = new Template("Written by <name> on <date>");
        assertEquals("Written by me on today", template.realize(
                Template.Sub.parse("name:me"),
                Template.Sub.parse("date: today")));
    }

    @Test
    public void testFormatSubs() {
        assertEquals("Written by me on today",
                Template.format("Written by <name> on <date>",
                        Template.Sub.parse("name :me"), Template.Sub.parse("date : today")));
    }

    @Test
    public void testFormatStrings() {
        assertEquals("Written by me on today",
                Template.format("Written by <name> on <date>",
                        "name:me", "date:today"));
    }

    @Test
    public void testFormatMap() {
        Map<String, Object> substitutions = Map.of(
                "string", "my string",
                "number", 5);
        assertEquals("Substituting a my string and a 5",
                Template.format("Substituting a <string> and a <number>", substitutions));
    }

}
