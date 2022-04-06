package ru.borisof.hessianman.loader.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class Interface {

    private String relativePath;
    private String applicationName;
    private String simpleName;
    private String className;
    private Collection<MethodDeclaration> methodsDeclaration;

}
