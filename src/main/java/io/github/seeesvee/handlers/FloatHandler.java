package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

/**
 * Float datatype handler.
 */
public class FloatHandler extends Handler {

    /**
     * Reads a Float value from a provided string when parsing from a CSV File.
     *
     * @param value Float Value in string form.
     * @param object Object to update class member variable.
     * @param field Field  / Class member variable to update.
     * @throws IllegalAccessException Cannot access the class member variable from the given Object.
     */
    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Float.parseFloat(value), object, field);
    }

    /**
     * Parses a Float from a provided field / class member variable from a provided object.
     *
     * @param object Object to parse Float value from.
     * @param field field / class member variable to parse from Object.
     * @return String value of a Float Object.
     * @throws IllegalAccessException Cannot access field / class member variable in given object.
     */
    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Float.toString((Float) getSafely(object, field));
    }

}
