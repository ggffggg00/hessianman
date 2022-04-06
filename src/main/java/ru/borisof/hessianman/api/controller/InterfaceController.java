package ru.borisof.hessianman.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.hessianman.loader.InterfaceHolderService;
import ru.borisof.hessianman.loader.model.Interface;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("interface")
public class InterfaceController {

    private final InterfaceHolderService interfaceHolderService;

    @GetMapping("{serviceName}")
    public Collection<Interface> getServiceInterfaces(@PathVariable final String serviceName) {
        return interfaceHolderService.getAllApplicationInterfaces(serviceName);
    }

    @GetMapping("{serviceName}/{interfaceName}")
    public Interface getServiceInterface(@PathVariable final String serviceName, @PathVariable final String interfaceName) {
        return interfaceHolderService.getInterface(serviceName, interfaceName);
    }


}
