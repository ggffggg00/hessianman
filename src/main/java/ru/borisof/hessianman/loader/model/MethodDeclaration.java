package ru.borisof.hessianman.loader.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Builder
public class MethodDeclaration {

    @Getter
    private String name;

    @JsonIgnore
    @Getter
    private Class<?> returnType;

    @Getter
    private MethodParameter[] methodParameters;

    @JsonProperty("returnType")
    public String getReturnTypeForJackson() {
        return returnType.getName();
    }


}
