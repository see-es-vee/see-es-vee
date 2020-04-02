package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

/**
 * Character datatype handler.
 */
public class CharacterHandler extends Handler {

    /**
     * Reads a Character value from a provided string when parsing from a CSV File.
     *
     * @param value Character Value in string form.
     * @param object Object to update class member variable.
     * @param field Field  / Class member variable to update.
     * @throws IllegalAccessException Cannot access the class member variable from the given Object.
     */
    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : value.charAt(0), object, field);
    }

    /**
     * Parses a Character from a provided field / class member variable from a provided object.
     *
     * @param object Object to parse Character value from.
     * @param field field / class member variable to parse from Object.
     * @return String value of a Character Object.
     * @throws IllegalAccessException Cannot access field / class member variable in given object.
     */
    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Character.toString((Character) getSafely(object, field));
    }

}
