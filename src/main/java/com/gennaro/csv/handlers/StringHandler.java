package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class StringHandler implements Handler{

    @Override
    public void handle(String value, Object object, Field field) throws IllegalAccessException {
        if(!field.canAccess(object)){
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        }
        else {
            field.set(object, value);
        }
    }
}
