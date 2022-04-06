package ru.borisof.hessianman.loader.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Builder
public class MethodParameter {

    @Getter
    private String name;

    @Getter
    @JsonIgnore
    private Class<?> argumentType;

    @JsonProperty("argumentType")
    public String getArgumentTypeForJson() {
        return argumentType.getName();
    }


}
