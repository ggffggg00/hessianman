package ru.borisof.hessianman.hessian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HessianRequestResult {

    private boolean isSuccess;
    private String errorMessage;
    private Class<?> returnType;
    private Object response;


}
