package com.gennaro.csv.handlers;

import java.lang.reflect.Field;
import java.util.HashMap;

public interface Handler {

    public void handle(String value, Object object, Field field) throws IllegalAccessException;


}
