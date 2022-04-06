package ru.borisof.hessianman.manifest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ServiceInterfaceDeclarationModel {

    @JsonProperty("name")
    private String interfaceName;

    @JsonProperty("subPath")
    private String subPath;

    @JsonProperty("targetType")
    private String typeClass;
}
