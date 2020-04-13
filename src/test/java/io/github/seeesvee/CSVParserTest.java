package io.github.seeesvee;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVParserTest {

    static String testCSV;
    static {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        testCSV = Objects.requireNonNull(cl.getResource("test.csv")).getFile();
    }

    @Test
    public void parseRowTest() throws IOException {

        CSVParser<TestClass> parser = new CSVParser<>(",", TestClass.class, null);
        String test = "test,\"\",\",hello\nworld\",\",,\",,hi";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);
        List<String> parsed = parser.parseRow(reader);

        assertArrayEquals(new String[]{"test", "\"\"", "\",hello\nworld\"", "\",,\"", "", "hi"}, parsed.toArray(new String[0]));
    }

    @Test
    void parseHeaderTest() throws IOException {

        CSVParser<TestClass> parser = new CSVParser<>(",", TestClass.class, null);
        String test = "9dad,*mom,69ian,\"gennaro\",ian and gennaro,ignorethis";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);

        Map<Integer, Field> testHeader = parser.parseHeader(reader);

        assertEquals(5, testHeader.keySet().size());
    }

    @Test
    void parseCSVTest() throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        CSVParser<TestClass> parser = new CSVParser<>(",", TestClass.class, null);
        List<TestClass> testParse = parser.parse(new File(testCSV));

        assertEquals(2, testParse.size());

    }

}
