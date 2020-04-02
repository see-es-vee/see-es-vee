package io.github.seeesvee;

import io.github.seeesvee.handlers.Handler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class model for a CSVWriterBuilder
 *
 * @version 1.0
 * @param <T> Class of Object that will be written to a CSV File.
 */
public final class CSVWriteBuilder<T> {

    private String delimiter = ",";
    private HashMap<Class<?>, ArrayList<Handler>> handlers = new HashMap<>();
    private Class<T> clazz;

    /**
     * Default constructor for a new CSVWriteBuilder.
     */
    public CSVWriteBuilder(){ }

    /**
     * Instantiates a new CSVWriter&lt;T&gt; based off the provided provided configuration(s).
     *
     * @return CSVWriter&lt;T&gt; Returns a CSVWriter with the specified class.
     */
    public CSVWriter<T> create(){

        if(this.handlers.size() == 0) handlers = null;
        if(this.clazz == null) throw new NullPointerException("Class must be set");
        return new CSVWriter<T>(delimiter, handlers, clazz);
    }

    /**
     * Sets the delimiter to configure what the CSVParser
     * will use to determine a new separated value.
     *
     * @param s The delimiter - Can be a singular character, or patter of characters.
     * @return this
     */
    public CSVWriteBuilder<T> setDelimiter(String s){
        this.delimiter = s;
        return this;
    }

    /**
     * Adds a custom made handler for additional
     * data structures created by the user.
     *
     * @param clazz Class of Data type to be handled.
     * @param handler Handler object that was created
     * @return this
     */
    public CSVWriteBuilder<T> addHandler(Class<?> clazz, Handler handler){
        if(!this.handlers.containsKey(clazz)){
            this.handlers.put(clazz, new ArrayList<>());
        }
        this.handlers.get(clazz).add(handler);
        return this;
    }

    /**
     * Sets the class for the CSVWriter.
     *
     * @param clazz Class of Object to be parsed from.
     * @return this
     */
    public CSVWriteBuilder<T> setClass(Class<T> clazz){
        this.clazz = clazz;
        return this;
    }

}
