package com.gennaro.csv.handlers;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class Handler {

    public abstract void handle(String value, Object object, Field field) throws IllegalAccessException;

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

}
