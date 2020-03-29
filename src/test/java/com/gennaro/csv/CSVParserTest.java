package com.gennaro.csv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVParserTest {


    @Test
    public void parseRowTest() throws IOException {
        CSVParser<TestClass> parser = new CSVParser<>(",", TestClass.class, null);
        String test = "test,\"\",\",hello\nworld\"";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);
        ArrayList<String> parsed = parser.parseRow(reader);

        assertArrayEquals(new String[]{"test", "\"\"", "\",hello\nworld\""}, parsed.toArray(new String[0]));
    }


    @Test
    void parseHeaderTest() throws IOException {

        CSVParser<TestClass> parser = new CSVParser<>(",", TestClass.class, null);
        String test = "9dad,*mom,69ian,\"gennaro\",ian and gennaro";
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);

        HashMap<Integer, Field> testHeader = parser.parseHeader(reader);

        assertEquals(TestClass.class.getFields().length, testHeader.keySet().size());
    }


    @Test
    void parseCSVTest() throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        CSVParser<TestClass> parser = new CSVParser<>(",", TestClass.class, null);
        ArrayList<TestClass> testParse = parser.parse(new File("dank.csv"));


        assertEquals(1, testParse.size());

    }

}
