package ru.borisof.hessianman.api.converter;

public interface MethodParameterDeserializer {

    Class<?> supportedClass();

    default boolean isSupports(Class<?> clazz) {
        return supportedClass().getName().equals(clazz.getName());
    }

    Object deserialize(Object data, Class<?> clazz);





}
