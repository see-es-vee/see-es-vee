package io.github.seeesvee;

import io.github.seeesvee.handlers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVWriter<T> {
    private final String DELIMITER;
    private final HashMap<Class<?>, ArrayList<Handler>> handlers = new HashMap<>();
    private final Class<T> clazz;


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

    public void write(File file, ArrayList<T> arrayList) throws IOException {

        FileWriter csvWriter = new FileWriter(file);
        Field[] fields = clazz.getDeclaredFields();

        for(int i = 0; i < fields.length; i++){

            csvWriter.append(fields[i].getName());
            if(i < fields.length-1){
                csvWriter.append(DELIMITER);
            }
        }
        csvWriter.append("\n");

        for(T data : arrayList){

            for(int i = 0; i < fields.length; i++){
                try {
                    fields[i].setAccessible(true);
                    //csvWriter.append("\"" + fields[i].get(data).toString() + "\"");

                    Handler handle = handlers.get(fields[i].getType()).get(0);
                    String fieldVal = handle.handleWrite(data, fields[i]);

                    csvWriter.append("\"").append(fieldVal).append("\"");

                    if(i < fields.length-1){
                        csvWriter.append(DELIMITER);
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();

    }

    void addHandler(Class<?> clazz, Handler handler){

        if(!handlers.containsKey(clazz)){
            handlers.put(clazz, new ArrayList<>());
        }
        handlers.get(clazz).add(handler);
    }


}
