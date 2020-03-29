package com.gennaro.csv;

import com.gennaro.csv.handlers.Handler;

import java.util.ArrayList;
import java.util.HashMap;

public final class CSVParseBuilder<T> {

    private String delimiter = ",";
    private HashMap<Class<?>, ArrayList<Handler>> handlers = new HashMap<>();
    private Class<T> clazz;

    public CSVParseBuilder(){ }

    public CSVParser<T> create(){

        if(this.handlers.size() == 0) handlers = null;
        if(this.clazz == null) throw new NullPointerException("Class must be set");
        return new CSVParser<T>(this.delimiter, this.clazz, this.handlers);
    }

    public CSVParseBuilder<T> setDelimiter(String s){
        this.delimiter = s;
        return this;
    }
    public CSVParseBuilder<T> addHandler(Class<?> clazz, Handler handler){
        if(!this.handlers.containsKey(clazz)){
            this.handlers.put(clazz, new ArrayList<>());
        }
        this.handlers.get(clazz).add(handler);
        return this;
    }
    public CSVParseBuilder<T> setClass(Class<T> clazz){
        this.clazz = clazz;
        return this;
    }

}
