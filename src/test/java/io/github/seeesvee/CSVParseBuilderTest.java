package io.github.seeesvee;

import io.github.seeesvee.handlers.Handler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVParseBuilderTest {
    static String testCSV;
    static {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        testCSV = Objects.requireNonNull(cl.getResource("test.csv")).getFile();
    }

    @Test
    void builderShouldSetClass() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        CSVParser<TestClass> t = new CSVParseBuilder<TestClass>()
                .setClass(TestClass.class)
                .create();
        Field classOfT = t.getClass().getDeclaredField("clazz");
        classOfT.setAccessible(true);
        Assertions.assertEquals(TestClass.class, classOfT.get(t));
        classOfT.setAccessible(false);
        t.parse(new File(testCSV));
    }

    @Test
    @SuppressWarnings("unchecked")
    void builderShouldAddHandler() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        CSVParser<TestClass> t = new CSVParseBuilder<TestClass>()
                .setClass(TestClass.class)
                .addHandler(TestClass.class, null)
                .addHandler(TestClass.class, null)
                .create();
        Field handlers = t.getClass().getDeclaredField("handlers");
        handlers.setAccessible(true);
        HashMap<Class<?>, ArrayList<Handler>> hands = (HashMap<Class<?>, ArrayList<Handler>>) handlers.get(t);
        Assertions.assertTrue(hands.containsKey(TestClass.class));
        Assertions.assertNull(hands.get(TestClass.class).get(0));
        handlers.setAccessible(false);
        t.parse(new File(testCSV));
    }

    @Test
    void builderShouldSetDelimiter() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {

        CSVParser<TestClass> t = new CSVParseBuilder<TestClass>()
                .setClass(TestClass.class)
                .setDelimiter(":")
                .create();
        Field DELIMITER = t.getClass().getDeclaredField("DELIMITER");
        DELIMITER.setAccessible(true);
        Assertions.assertEquals(":", DELIMITER.get(t));
        t.parse(new File(testCSV));

    }

    @Test
    void builderShouldNotAllowNullClass() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {

        CSVParseBuilder<TestClass> t = new CSVParseBuilder<TestClass>()
                .setClass(null)
                .setDelimiter(":");
        Exception exception = assertThrows(NullPointerException.class, t::create);

    }

}
