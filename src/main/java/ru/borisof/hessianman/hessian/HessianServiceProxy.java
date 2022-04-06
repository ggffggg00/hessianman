package ru.borisof.hessianman.hessian;

public interface HessianServiceProxy {

    Object getServiceProxy(String applicationName, String interfaceName, String url);

    Object getServiceProxy(String applicationName, String interfaceName, String url, String username, String password);

}
