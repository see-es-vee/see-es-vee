package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

public abstract class Handler {

    public abstract void handleRead(String value, Object object, Field field) throws IllegalAccessException;
    public abstract String handleWrite(Object object, Field field) throws IllegalAccessException;


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
