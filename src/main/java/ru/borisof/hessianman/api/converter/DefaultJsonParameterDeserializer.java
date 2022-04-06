package ru.borisof.hessianman.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DefaultJsonParameterDeserializer implements MethodParameterDeserializer{

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Class<?> supportedClass() {
        return null;
    }

    //Для поддержки всех типов, отдаем true
    @Override
    public boolean isSupports(final Class<?> clazz) {
        return true;
    }

    @Override
    public Object deserialize(final Object data, final Class<?> clazz) {
        try {
            return objectMapper.readValue(String.valueOf(data), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Parse data failed", e);

        }
    }
}
