package ru.borisof.hessianman.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.hessianman.manifest.Manifest;
import ru.borisof.hessianman.manifest.ManifestHolder;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("manifest")
public class ManifestController {

    private final ManifestHolder manifestHolder;

    @GetMapping
    public Collection<Manifest> getAllManifests() {
        return manifestHolder.getAllManifests();
    }


}
