package ru.borisof.hessianman.api.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ParameterDeserializerHolder {

    private final Set<MethodParameterDeserializer> methodParameterDeserializerSet;

    public MethodParameterDeserializer getDeserializerByType(Class<?> clazz) {
        return methodParameterDeserializerSet.stream()
                .filter((el) -> el.isSupports(clazz))
                .findFirst().orElseThrow(() -> new RuntimeException("Deserializer not found for " + clazz.getName()));
    }

}
