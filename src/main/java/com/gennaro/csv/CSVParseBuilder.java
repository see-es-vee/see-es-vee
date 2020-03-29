package com.gennaro.csv;

import com.gennaro.csv.handlers.Handler;

import java.util.ArrayList;
import java.util.HashMap;

public final class CSVParseBuilder<T> {

    private String delimiter = ",";
    private HashMap<Class<?>, Handler> handlers = new HashMap<>();
    private Class<T> clazz;

    public CSVParseBuilder(){ }

    public CSVParser<T> create(){

        if(this.handlers.size() == 0) handlers = null;

        return new CSVParser<T>(this.delimiter, this.clazz, this.handlers);
    }

    public void setDelimiter(String s){
        this.delimiter = s;
    }
    public void addHandler(Class<?> clazz, Handler handler){
        this.handlers.put(clazz, handler);
    }
    public void setClass(Class<T> clazz){
        this.clazz = clazz;
    }

}
