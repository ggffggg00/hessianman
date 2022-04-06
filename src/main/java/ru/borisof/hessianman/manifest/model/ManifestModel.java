package ru.borisof.hessianman.manifest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.borisof.hessianman.manifest.Manifest;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ManifestModel implements Manifest {

    private String name;
    private String artifactUrl;

    @JsonProperty("path")
    private String rootPath;
    private String username;
    private String password;

    @JsonProperty("services")
    private Set<ServiceInterfaceDeclarationModel> interfaces;


    @Override
    public String getServiceName() {
        return this.name;
    }

    @Override
    public String getInterfacesArtifactUrl() {
        return this.artifactUrl;
    }

    @Override
    public String getHessianRootPath() {
        return this.rootPath;
    }

    @Override
    public String getServiceUsername() {
        return this.username;
    }

    @Override
    public String getServicePassword() {
        return this.password;
    }

    @Override
    public Set<ServiceInterfaceDeclarationModel> getInterfaces() {
        return this.interfaces;
    }


}
