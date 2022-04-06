package ru.borisof.hessianman.manifest;

import java.util.Collection;

public interface ManifestHolder {

    Manifest getManifestByName(String name);

    Collection<Manifest> getAllManifests();

    boolean contains(String name);

    int manifestCount();

}
