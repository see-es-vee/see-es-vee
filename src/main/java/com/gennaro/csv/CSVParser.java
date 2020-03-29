package com.gennaro.csv;

import com.gennaro.csv.handlers.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public final class CSVParser<T> {

    private final String DELIMITER;
    private final Class<T> classOfT;
    private final HashMap<Class<?>, ArrayList<Handler>> handlers = new HashMap<>();


    CSVParser(String delimiter, Class<T> classOfT, HashMap<Class<?>, Handler> additionalHandlers){
        this.DELIMITER = delimiter;
        this.classOfT = classOfT;

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
            additionalHandlers.forEach(this::addHandler);
        }
    }

    public ArrayList<T> parse(File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        BufferedReader reader = new BufferedReader(new FileReader(file));

        HashMap<Integer, Field> header = parseHeader(reader);

        return parseCSV(reader, header);

    }

    HashMap<Integer, Field> parseHeader(BufferedReader file) throws IOException {

        HashMap<Integer, Field> header = new HashMap<Integer, Field>();

        ArrayList<String> headerList = parseRow(file);

        for(int i = 0; i < headerList.size(); i++){
            try {

                String headerStr = headerList.get(i);
                headerStr = headerStr.replace(' ', '_');
                headerStr = headerStr.replaceAll("[^a-zA-Z0-9_]", "");
                headerStr = headerStr.replaceFirst("^[0-9]+", "");

                Field field = classOfT.getDeclaredField(headerStr);
                header.put(i, field);

            } catch(NoSuchFieldException e) {

            }
        }

        return header;
    }

    ArrayList<T> parseCSV(BufferedReader csv, HashMap<Integer, Field> header) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        ArrayList<T> parsedCSVList = new ArrayList<T>();

        ArrayList<String> parsedLine = parseRow(csv);

        while(parsedLine.size() != 0){

            T object = (T) classOfT.getDeclaredConstructor().newInstance(new Object[]{});

            if(parsedLine.size() == header.size()){
                for(Integer i : header.keySet()){
                    Field field = header.get(i);
                    handleValue(field, parsedLine.get(i), object);
                }
            }

            parsedLine = parseRow(csv);
            parsedCSVList.add(object);
        }

        return parsedCSVList;
    }

    ArrayList<String> parseRow(BufferedReader file) throws IOException {

        ArrayList<String> stringsArray = new ArrayList<String>();
        StringBuilder string = new StringBuilder();

        boolean quoteOpen = false;
        boolean addedThisCycle = false;
        do{
            String line = file.readLine();
            if(line == null) break;
            String[] splitLine = line.split(DELIMITER);

            for(int i = 0; i < splitLine.length; i++){
                String str = splitLine[i];

                if(quoteOpen && i != 0){
                    string.append(DELIMITER);
                }

                if(str.length() != 0 || quoteOpen) {
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

                    if(!quoteOpen){
                        stringsArray.add(string.toString());
                        string.setLength(0);
                    }
                }
                else{
                    stringsArray.add("");
                }
                addedThisCycle = false;
            }

            if(quoteOpen)string.append("\n");

        } while(quoteOpen);

        return stringsArray;
    }

    void addHandler(Class<?> clazz, Handler handler){

        if(!handlers.containsKey(clazz)){
            handlers.put(clazz, new ArrayList<>());
        }
        handlers.get(clazz).add(handler);
    }

    void handleValue(Field field, String value, T object) throws NoSuchMethodException {

        if(!handlers.containsKey(field.getType())) throw new NoSuchMethodException("");

        handlers.get(field.getType()).forEach(v -> {
            try {
                v.handle(value, object, field);
            } catch (IllegalAccessException e) {

            }
        });

    }
}
