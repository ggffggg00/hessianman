package ru.borisof.hessianman.manifest;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.HashMap;

public class DefaultMapManifestHolder extends HashMap<String, Manifest> implements ManifestHolder {

    public void addAll(Collection<? extends Manifest> manifests) {
        manifests.forEach(manifest -> put(manifest.getServiceName(), manifest));
    }

    @Override
    @Nullable
    public Manifest getManifestByName(String name) {
        return get(name);
    }

    @Override
    public Collection<Manifest> getAllManifests() {
        return values();
    }

    @Override
    public boolean contains(final String name) {
        return containsKey(name);
    }

    @Override
    public int manifestCount() {
        return size();
    }
}
