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
        System.out.println("created new file writer - Successful");
        Field[] fields = clazz.getDeclaredFields();

        for(int i = 0; i < fields.length; i++){

            csvWriter.append(fields[i].getName());
            System.out.println("fields[i].getName() " + fields[i].getName());
            if(i < fields.length-1){
                csvWriter.append(DELIMITER);
            }
        }
        csvWriter.append("\n");

        for(T data : arrayList){

            for(int i = 0; i < fields.length; i++){
                System.out.println("START LOOP: " + i);
                try {
                    System.out.println("Trying to set field accessible");
                    fields[i].setAccessible(true);
                    System.out.println("Trying to set field accessible SUCCESS");

                    System.out.println("GETTING HANDLER: " + fields[i].getType().getName());
                    Handler handle = handlers.get(fields[i].getType()).get(0);
                    System.out.println("GETTING HANDLER SUCCESS");

                    System.out.println("GETTING FIELD VALUE");
                    String fieldVal = handle.handleWrite(data, fields[i]);
                    System.out.println("GETTING FIELD VALUE SUCCESS");

                    System.out.println("Appending val: " + fieldVal);
                    csvWriter.append("\"").append(fieldVal).append("\"");

                    System.out.println("if(" + i + "< " + (fields.length-1));
                    if(i < fields.length-1){
                        System.out.println("APPENDING DELIMITER");
                        csvWriter.append(DELIMITER);
                        System.out.println("APPENDING SUCCESS");
                    }
                } catch (IllegalAccessException ignored) {
                }
                System.out.println("NEXT LOOP: " + i);
            }
            System.out.println("APENDING \"n");
            csvWriter.append("\n");
            System.out.println("APENDING \"n sccuess");
        }
        System.out.println("Attempting to flush");
        csvWriter.flush();
        System.out.println("Flush success");
        System.out.println("Attempting to close.");
        csvWriter.close();
        System.out.println("close success..");


    }

    void addHandler(Class<?> clazz, Handler handler){

        if(!handlers.containsKey(clazz)){
            handlers.put(clazz, new ArrayList<>());
        }
        handlers.get(clazz).add(handler);
    }


}
