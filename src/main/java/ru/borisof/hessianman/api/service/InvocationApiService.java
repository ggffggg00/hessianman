package ru.borisof.hessianman.api.service;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import ru.borisof.hessianman.api.converter.ParameterDeserializerHolder;
import ru.borisof.hessianman.api.dto.InvocationRequest;
import ru.borisof.hessianman.api.exception.InvocationException;
import ru.borisof.hessianman.api.exception.ValidationException;
import ru.borisof.hessianman.hessian.HessianRequestBuilder;
import ru.borisof.hessianman.hessian.HessianRequestExecutor;
import ru.borisof.hessianman.hessian.HessianRequestResult;
import ru.borisof.hessianman.loader.InterfaceHolderService;
import ru.borisof.hessianman.loader.model.Interface;
import ru.borisof.hessianman.loader.model.MethodDeclaration;
import ru.borisof.hessianman.loader.model.MethodParameter;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class InvocationApiService {

    private final InterfaceHolderService interfaceHolder;
    private final HessianRequestExecutor requestExecutor;
    private final ParameterDeserializerHolder parameterDeserializerHolder;

    public HessianRequestResult invoke(String applicationName, String interfaceName, String methodName,
                                       InvocationRequest reqDetails) {
        Interface i = interfaceHolder.getInterface(applicationName, interfaceName);

        MethodDeclaration methodDeclaration = i.getMethodsDeclaration().stream()
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getMethodParameters().length == reqDetails.getMethodArgs().size())
                .findFirst().orElseThrow(() -> new ValidationException("Can't found target method"));

        reqDetails.getMethodArgs().entrySet()
                .forEach(entry -> {
                    var paramDeclaration = findMethodParamByName(methodDeclaration, entry.getKey());
                    if (!isNeedToDeserialize(paramDeclaration.getArgumentType())) {
                        return;
                    }
                    entry.setValue(parameterDeserializerHolder.getDeserializerByType(paramDeclaration.getArgumentType())
                            .deserialize(entry.getValue(), paramDeclaration.getArgumentType()));
                });

        HessianRequestBuilder requestBuilder = HessianRequestBuilder.getInstance(i, methodDeclaration)
                .url(reqDetails.getUrl())
                .username(reqDetails.getUsername())
                .password(reqDetails.getPassword());

        reqDetails.getMethodArgs().forEach(requestBuilder::parameter);

        HessianRequestResult hessianRequestResult;
        try {
            hessianRequestResult = requestExecutor.executeRequest(requestBuilder.build());
        } catch (Exception e){
            throw new InvocationException(e);
        }

        return hessianRequestResult;

    }

    private MethodParameter findMethodParamByName(MethodDeclaration methodDeclaration, String name) {
        return Arrays.stream(methodDeclaration.getMethodParameters())
                .filter(param -> param.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Unknown method argument name: " + name));
    }

    private boolean isNeedToDeserialize(Class<?> parameterType) {
        return !parameterType.getName().startsWith("java.lang");
    }


}
