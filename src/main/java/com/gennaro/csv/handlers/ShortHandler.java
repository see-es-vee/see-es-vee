package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class ShortHandler extends Handler {
    @Override
    public void handle(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Short.parseShort(value), object, field);
    }
}
