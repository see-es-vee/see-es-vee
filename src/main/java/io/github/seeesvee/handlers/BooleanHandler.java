package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

public class BooleanHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Boolean.parseBoolean(value), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Boolean.toString((Boolean) getSafely(object, field));
    }

}
