package ru.borisof.hessianman.manifest;

import ru.borisof.hessianman.manifest.model.ServiceInterfaceDeclarationModel;

import java.util.Set;

public interface Manifest {

    String getServiceName();

    String getInterfacesArtifactUrl();

    String getHessianRootPath();

    String getServiceUsername();

    String getServicePassword();

    Set<ServiceInterfaceDeclarationModel> getInterfaces();


}
