package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class ShortHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Short.parseShort(value), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Short.toString((Short) getSafely(object, field));
    }

}
