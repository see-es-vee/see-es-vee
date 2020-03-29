package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class CharacterHandler extends Handler {
    @Override
    public void handle(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : value.charAt(0), object, field);
    }
}
