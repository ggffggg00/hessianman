package ru.borisof.hessianman.loader;

import ru.borisof.hessianman.loader.model.Interface;

public interface InterfaceLoader {

    Class<?> getClassForInterface(Interface anInterface);

    Class<?> getClassForInterface(String interfaceName);

    ClassLoader getClassLoader();

}
