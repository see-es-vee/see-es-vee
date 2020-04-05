package io.github.seeesvee.handlers;

import java.lang.reflect.Field;
import java.text.ParseException;

/**
 * Handler model for handling datatypes.
 */
public abstract class Handler {

    /**
     * Default method header reading in a datatype.
     *
     * @param value String value read in from CSV File.
     * @param object Object to add value you to.
     * @param field field / class member variable within the object to add value to.
     * @throws IllegalAccessException Cannot access the field /class member variable from the given Object.
     */
    public abstract void handleRead(String value, Object object, Field field) throws IllegalAccessException;

    /**
     * Default method header for taking in a specific datatype and returning its value as a string.
     *
     * @param object object to read value from.
     * @param field field / class member variable within a specific object to retrieve string value from.
     * @return String value of field / class member variable.
     * @throws IllegalAccessException Cannot access the field / class member variable from the given Object.
     */
    public abstract String handleWrite(Object object, Field field) throws IllegalAccessException;

    /**
     * Sets a field / class member variable to a specific value given in the format of a String.
     *
     * @param value value to be set in field / class member variable.
     * @param object object to self value in.
     * @param field field / class member variable within object to set to value.
     * @throws IllegalAccessException Cannot access the field / class member variable from the given Object.
     */
    protected void setSafely(Object value, Object object, Field field) throws IllegalAccessException {
        boolean needReset = false;
        if(!field.canAccess(object)) {
            field.setAccessible(true);
            needReset = true;
        }
        field.set(object, value);
        if(needReset) {
            field.setAccessible(false);
        }
    }

    /**
     * Retrieves a field / class member variable from a given object, and returns it as its current datatype within the object.
     *
     * @param object object to retrieve field / class member value from.
     * @param field field / class member variable within object to retrieve string value from.
     * @return a field / class member variable in its original datatype form.
     * @throws IllegalAccessException Cannot access the field / class member variable from the given Object.
     */
    protected Object getSafely(Object object, Field field) throws IllegalAccessException {
        boolean needReset = false;
        if(!field.canAccess(object)) {
            field.setAccessible(true);
            needReset = true;
        }
        Object returnObject = field.get(object);
        if(needReset){
            field.setAccessible(false);
        }
        return returnObject;
    }


}
