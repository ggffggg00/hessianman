package ru.borisof.hessianman.hessian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.borisof.hessianman.loader.model.Interface;
import ru.borisof.hessianman.loader.model.MethodDeclaration;

@AllArgsConstructor
@Getter
public class HessianRequest {

    private final String applicationName;
    private final String interfaceName;
    private final MethodDeclaration methodDeclaration;
    private final Object[] parameters;
    private String username;
    private String password;
    private String url;

}
