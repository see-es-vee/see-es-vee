package io.github.seeesvee;

import io.github.seeesvee.handlers.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVParseBuilder Model
 *
 * @version 1.0
 * @param <T> Class of Object you're looking to parse into.
 */
public final class CSVParseBuilder<T> {

    private String delimiter = ",";
    private Map<Class<?>, List<Handler>> handlers = new HashMap<>();
    private Class<T> clazz;

    /**
     * Default constructor for CSVParseBuilder.
     */
    public CSVParseBuilder(){ }

    /**
     * Invokes the creation of a new CSVParser&lt;T&gt; based off of the provided configuration(s).
     *
     * @return CSVParser&lt;T&gt; Returns a CSVParser with the specified class.
     */
    public CSVParser<T> create(){
        if(this.handlers.size() == 0) handlers = null;
        if(this.clazz == null) throw new NullPointerException("Class must be set");
        return new CSVParser<T>(this.delimiter, this.clazz, this.handlers);
    }

    /**
     * Sets the delimiter to configure what the CSVParser
     * will use to determine a new separated value.
     *
     * @param s The delimiter - Can be a singular character, or patter of characters.
     * @return this
     */
    public CSVParseBuilder<T> setDelimiter(String s){
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
    public CSVParseBuilder<T> addHandler(Class<?> clazz, Handler handler){
        if(!this.handlers.containsKey(clazz)){
            this.handlers.put(clazz, new ArrayList<>());
        }
        this.handlers.get(clazz).add(handler);
        return this;
    }

    /**
     * Sets the class for the CSVParser.
     *
     * @param clazz Class of Object to be parsed into.
     * @return this
     */
    public CSVParseBuilder<T> setClass(Class<T> clazz){
        this.clazz = clazz;
        return this;
    }

}
