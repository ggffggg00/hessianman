package ru.borisof.hessianman.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.hessianman.api.dto.InvocationRequest;
import ru.borisof.hessianman.api.service.InvocationApiService;
import ru.borisof.hessianman.hessian.HessianRequestResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("invoke")
public class InvocationController {

    private final InvocationApiService invocationApiService;

    @PostMapping
    public HessianRequestResult invokeMethod(@RequestBody InvocationRequest invocationRequest) {
        return invocationApiService.invoke(
                invocationRequest.getServiceName(),
                invocationRequest.getInterfaceName(),
                invocationRequest.getMethodName(),
                invocationRequest);
    }

    @PostMapping("{applicationName}/{interfaceName}/{methodName}")
    public HessianRequestResult invokeMethodAlternative(@RequestBody InvocationRequest invocationRequest,
                                                        @PathVariable final String applicationName,
                                                        @PathVariable final String interfaceName,
                                                        @PathVariable final String methodName) {
        return invocationApiService.invoke(applicationName, interfaceName, methodName, invocationRequest);
    }


}
