package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class ByteHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Byte.parseByte(value), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Byte.toString((Byte) getSafely(object, field));
    }
}
