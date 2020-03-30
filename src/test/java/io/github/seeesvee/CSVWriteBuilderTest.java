package io.github.seeesvee;

import io.github.seeesvee.handlers.Handler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVWriteBuilderTest {

    @Test
    void builderShouldSetClass() throws NoSuchFieldException, IllegalAccessException {

        CSVWriter<TestClass> t = new CSVWriteBuilder<TestClass>()
                .setClass(TestClass.class)
                .create();

        Field classOfT = t.getClass().getDeclaredField("clazz");
        classOfT.setAccessible(true);
        Assertions.assertEquals(TestClass.class, classOfT.get(t));
        classOfT.setAccessible(false);

    }

    @Test
    void builderShouldAddHandler() throws NoSuchFieldException, IllegalAccessException {

        CSVWriter<TestClass> t = new CSVWriteBuilder<TestClass>()
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

    }

    @Test
    void builderShouldSetDelimiter() throws NoSuchFieldException, IllegalAccessException {

        CSVWriter<TestClass> t = new CSVWriteBuilder<TestClass>()
                .setClass(TestClass.class)
                .setDelimiter(":")
                .create();
        Field DELIMITER = t.getClass().getDeclaredField("DELIMITER");
        DELIMITER.setAccessible(true);
        Assertions.assertEquals(":", DELIMITER.get(t));

    }

    @Test
    void builderShouldNotAllowNullClass()  {

        CSVWriteBuilder<TestClass> t = new CSVWriteBuilder<TestClass>()
                .setClass(null)
                .setDelimiter(":");
        Exception exception = assertThrows(NullPointerException.class, t::create);

    }
}
