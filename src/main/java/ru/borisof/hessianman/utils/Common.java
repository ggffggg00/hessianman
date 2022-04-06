package ru.borisof.hessianman.utils;

public abstract class Common {

    public static String getOrDefault(String initialValue, String defaultValue ) {
        return initialValue == null || initialValue.isEmpty() ? defaultValue : initialValue;
    }
}
