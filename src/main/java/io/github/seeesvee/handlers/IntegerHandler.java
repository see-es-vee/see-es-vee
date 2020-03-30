package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

public class IntegerHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Integer.parseInt(value), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Integer.toString((Integer) getSafely(object, field));
    }

}
