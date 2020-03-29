package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class LongHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Long.parseLong(value), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Long.toString((Long) getSafely(object, field));
    }

}
