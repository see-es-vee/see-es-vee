package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

/**
 * Double datatype handler.
 */
public class DoubleHandler extends Handler {

    /**
     * Reads a Double value from a provided string when parsing from a CSV File.
     *
     * @param value Double Value in string form.
     * @param object Object to update class member variable.
     * @param field Field  / Class member variable to update.
     * @throws IllegalAccessException Cannot access the class member variable from the given Object.
     */
    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Double.parseDouble(value), object, field);
    }

    /**
     * Parses a Double from a provided field / class member variable from a provided object.
     *
     * @param object Object to parse Double value from.
     * @param field field / class member variable to parse from Object.
     * @return String value of a Double Object.
     * @throws IllegalAccessException Cannot access field / class member variable in given object.
     */
    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Double.toString((Double) getSafely(object, field));
    }

}
