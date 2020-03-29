package com.gennaro.csv.handlers;

import java.lang.reflect.Field;

public class DoubleHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : Double.parseDouble(value), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Double.toString((Double) getSafely(object, field));
    }

}
