package io.github.seeesvee;

import io.github.seeesvee.handlers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CSVParser model.

 * @version 1.0
 * @param <T> Class of Object to be parsed from.
 */
public class CSVWriter<T> {

    private final String DELIMITER;
    private final HashMap<Class<?>, ArrayList<Handler>> handlers = new HashMap<>();
    private final Class<T> clazz;

    /**
     * Default constructor for a CSVWriter.
     *
     * @param delimiter Delimiter to be used when writing to a CSV File.
     * @param additionalHandlers Additionally needed handlers for data types.
     * @param clazz Class of <T> Object being parsed from.
     */
    CSVWriter(String delimiter, HashMap<Class<?>, ArrayList<Handler>> additionalHandlers, Class<T> clazz){
        this.DELIMITER = delimiter;
        this.clazz = clazz;

        addHandler(String.class, new StringHandler());
        addHandler(Character.class, new CharacterHandler());
        addHandler(Boolean.class, new BooleanHandler());
        addHandler(Byte.class, new ByteHandler());
        addHandler(Double.class, new DoubleHandler());
        addHandler(Float.class, new FloatHandler());
        addHandler(Long.class, new LongHandler());
        addHandler(Short.class, new ShortHandler());
        addHandler(Integer.class, new IntegerHandler());

        if(additionalHandlers != null){
            additionalHandlers.forEach((k,v) -> {
                v.forEach((v2 -> this.addHandler(k,v2)));
            });
        }
    }

    /**
     * Primariy driver for writing &lt;T&gt; Objects to the provided CSV File.
     *
     * @param file CSV File to write to.
     * @param arrayList ArrayList of &lt;T&gt; Objects to parse and write from.
     * @throws IOException File location not accessible.
     */
    public void write(File file, ArrayList<T> arrayList) throws IOException {

        FileWriter csvWriter = new FileWriter(file);
        Field[] fields = clazz.getDeclaredFields();
        ArrayList<Field> validFields = new ArrayList<>();

        for(Field field : fields){
            if(handlers.containsKey(field.getType())){
                validFields.add(field);
            }
        }

        for(int i = 0; i < validFields.size(); i++){
            csvWriter.append(validFields.get(i).getName());
            if (i < validFields.size() - 1) {
                csvWriter.append(DELIMITER);
            }
        }

        csvWriter.append("\n");

        for(T data : arrayList){

            for(int i = 0; i < validFields.size(); i++){
                try {
                    validFields.get(i).setAccessible(true);

                    Handler handle = handlers.get(validFields.get(i).getType()).get(0);

                    String fieldVal = handle.handleWrite(data, validFields.get(i));

                    csvWriter.append("\"").append(fieldVal).append("\"");

                    if (i < validFields.size() - 1) {
                        csvWriter.append(DELIMITER);
                    }

                } catch (IllegalAccessException ignored) { }
            }
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }

    /**
     * Adds custom Handler objects to be used for parsing.
     *
     * @param clazz Class of Object to be handled.
     * @param handler Handler Object to be added.
     */
    void addHandler(Class<?> clazz, Handler handler){

        if(!handlers.containsKey(clazz)){
            handlers.put(clazz, new ArrayList<>());
        }
        handlers.get(clazz).add(handler);
    }


}
