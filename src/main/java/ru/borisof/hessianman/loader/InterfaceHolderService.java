package ru.borisof.hessianman.loader;

import ru.borisof.hessianman.loader.model.Interface;

import java.util.Collection;

public interface InterfaceHolderService {

    Interface getInterface(String applicationName, String interfaceSimpleName);

    Collection<Interface> getAllApplicationInterfaces(String applicationName);

}
