package io.github.seeesvee.handlers;

import java.lang.reflect.Field;

public class CharacterHandler extends Handler {

    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException {
        this.setSafely(value.length() < 1 ? null : value.charAt(0), object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        return Character.toString((Character) getSafely(object, field));
    }

}
