package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

/**
 * Byte datatype handler.
 */
public class ByteHandler extends Handler {

    /**
     * Reads a Byte value from a provided string when parsing from a CSV File.
     *
     * @param value Byte Value in string form.
     * @param object Object to update class member variable.
     * @param field Field  / Class member variable to update.
     * @throws IllegalAccessException Cannot access the class member variable from the given Object.
     */
    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Byte.parseByte(value), object, field);
    }

    /**
     * Parses a Byte from a provided field / class member variable from a provided object.
     *
     * @param object Object to parse Byte value from.
     * @param field field / class member variable to parse from Object.
     * @return String value of a Byte Object.
     * @throws IllegalAccessException Cannot access field / class member variable in given object.
     */
    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Byte.toString((Byte) getSafely(object, field));
    }
}
