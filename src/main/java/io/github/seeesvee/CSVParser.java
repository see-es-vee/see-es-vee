package io.github.seeesvee;

import io.github.seeesvee.handlers.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVParser model.
 *
 * @param <T> Class of Object to be parsed into.
 * @version 1.0
 */
public final class CSVParser<T> {
    private final String DELIMITER;
    private final Class<T> clazz;
    private final Map<Class<?>, List<Handler>> handlers = new HashMap<>();

    /**
     * Default constructor for CSVParser.
     *
     * @param delimiter          Delimiter to be used for parsing.
     * @param classOfT           Class that will be used to parse into.
     * @param additionalHandlers ArrayList of additionally added handles by user.
     */
    CSVParser(String delimiter, Class<T> classOfT, Map<Class<?>, List<Handler>> additionalHandlers) {
        this.DELIMITER = delimiter;
        this.clazz = classOfT;

        addHandler(String.class, new StringHandler());
        addHandler(Character.class, new CharacterHandler());
        addHandler(Boolean.class, new BooleanHandler());
        addHandler(Byte.class, new ByteHandler());
        addHandler(Double.class, new DoubleHandler());
        addHandler(Float.class, new FloatHandler());
        addHandler(Long.class, new LongHandler());
        addHandler(Short.class, new ShortHandler());
        addHandler(Integer.class, new IntegerHandler());

        if (additionalHandlers != null) {
            additionalHandlers.forEach((k, v) -> v.forEach((v2 -> this.addHandler(k, v2))));
        }
    }

    /**
     * CSVParser driver. This invokes all methods for parsing a CSV file.
     *
     * @param file File to parse from.
     * @return ArrayList of parsed objects based off the given class.
     * @throws IOException               File found / accessible.
     * @throws InvocationTargetException Attempting to Invoke a null object.
     * @throws NoSuchMethodException     Cannot find constructor for &lt;T&gt;Class.
     * @throws InstantiationException    Failed to create new instance of &lt;T&gt;Object
     * @throws IllegalAccessException    Failed to access class member variable of &lt;T&gt;Object
     */
    public List<T> parse(File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Map<Integer, Field> header = parseHeader(reader);
        return parseCSV(reader, header);
    }

    /**
     * Parses the header line of the a given CSV File.
     *
     * @param file CSV File to parse.
     * @return HashMap of &lt;Integer, String&gt; Where Integer is the index of the header value.
     * @throws IOException CSV File cannot be found / accessed.
     */
    protected Map<Integer, Field> parseHeader(BufferedReader file) throws IOException {
        Map<Integer, Field> header = new HashMap<>();
        List<String> headerList = parseRow(file);

        for (int i = 0; i < headerList.size(); i++) {
            try {

                String headerStr = headerList.get(i);
                headerStr = headerStr.replace(' ', '_');
                headerStr = headerStr.replaceAll("[^a-zA-Z0-9_]", "");
                headerStr = headerStr.replaceFirst("^[0-9]+", "");

                Field field = clazz.getDeclaredField(headerStr);
                header.put(i, field);

            } catch (NoSuchFieldException e) {

            }
        }
        return header;
    }

    /**
     * Parses all values in a CSV File.
     *
     * @param file   CSV File to be parsed.
     * @param header header based off of field / class member variable names in &lt;T&gt;Class
     * @return ArrayList of &lt;T&gt;Class Objects.
     * @throws IOException               CSV File not found / accessible.
     * @throws IllegalAccessException    Failed to access class member variable of &lt;T&gt;Object
     * @throws InvocationTargetException Attempting to Invoke a null object.
     * @throws NoSuchMethodException     Cannot find constructor for &lt;T&gt;Class.
     * @throws InstantiationException    Error creating new &lt;T&gt; Object.
     */
    protected List<T> parseCSV(BufferedReader file, Map<Integer, Field> header) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<T> parsedCSVList = new ArrayList<>();
        List<String> parsedLine = parseRow(file);

        while (parsedLine.size() != 0) {
            T object = clazz.getDeclaredConstructor().newInstance();

            for (Integer i : header.keySet()) {
                Field field = header.get(i);
                handleValue(field, parsedLine.get(i), object);
            }

            parsedLine = parseRow(file);
            parsedCSVList.add(object);
        }
        return parsedCSVList;
    }

    /**
     * Parses a single row from provided CSV File.
     *
     * @param file CSV File to be parsed.
     * @return ArrayList of Strings containing parsed strings based off of the delimiter.
     * @throws IOException CSV file not found / accessible.
     */
    protected List<String> parseRow(BufferedReader file) throws IOException {
        List<String> stringsArray = new ArrayList<>();
        StringBuilder string = new StringBuilder();

        boolean quoteOpen = false;
        boolean addedThisCycle = false;
        do {
            String line = file.readLine();
            if (line == null) break;
            String[] splitLine = line.split(DELIMITER, -1);

            for (int i = 0; i < splitLine.length; i++) {
                String str = splitLine[i];

                if (quoteOpen && i != 0) {
                    string.append(DELIMITER);
                }

                if (str.length() != 0 || quoteOpen) {
                    if (!quoteOpen && str.charAt(0) == '\"') {
                        if (str.length() == 1 || str.charAt(1) != '\"') {
                            quoteOpen = true;
                            addedThisCycle = true;
                        }
                    }

                    string.append(str);

                    if (!(addedThisCycle && str.length() <= 1) && str.length() > 0 && str.charAt(str.length() - 1) == '\"') {
                        if (str.length() == 1 || str.charAt(str.length() - 2) != '\"') {
                            quoteOpen = false;
                        }
                    }

                    if (!quoteOpen) {
                        stringsArray.add(string.toString());
                        string.setLength(0);
                    }
                } else {
                    stringsArray.add("");
                }
                addedThisCycle = false;
            }

            if (quoteOpen) {
                string.append("\n");
            }

        } while (quoteOpen);

        return stringsArray;
    }

    /**
     * Adds custom Handler objects to be used for parsing.
     *
     * @param clazz   Class of Object to be handled.
     * @param handler Handler Object to be added.
     */
    void addHandler(Class<?> clazz, Handler handler) {
        if (!handlers.containsKey(clazz)) {
            handlers.put(clazz, new ArrayList<>());
        }
        handlers.get(clazz).add(handler);
    }

    /**
     * Calls Handler based off of the &lt;T&gt; Object.&lt;?&gt;ClassMemberVariable.
     *
     * @param field  field of &lt;T&gt; Object attempting to be parsed from CSV File.
     * @param value  String valued parsed from the CSV File.
     * @param object new instance of &lt;?&gt; Object that is being added to &lt;T&gt; Object.
     * @throws NoSuchMethodException Handlers ArrayList does not contain a Handler for &lt;?&gt;field.
     */
    void handleValue(Field field, String value, T object) throws NoSuchMethodException {
        if (!handlers.containsKey(field.getType())) {
            throw new NoSuchMethodException("");
        }

        handlers.get(field.getType()).forEach(v -> {
            try {
                v.handleRead(value, object, field);
            } catch (IllegalAccessException e) {

            }
        });

    }
}
