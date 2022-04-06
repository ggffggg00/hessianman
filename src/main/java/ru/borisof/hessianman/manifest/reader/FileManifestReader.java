package ru.borisof.hessianman.manifest.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.borisof.hessianman.manifest.Manifest;
import ru.borisof.hessianman.manifest.ManifestReader;
import ru.borisof.hessianman.manifest.exception.ManifestReadException;
import ru.borisof.hessianman.manifest.model.ManifestModel;
import ru.borisof.hessianman.utils.LambdaUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("defaultManifestReader")
@RequiredArgsConstructor
@Slf4j
public class FileManifestReader implements ManifestReader {

    @Qualifier("manifestObjectMapper")
    private final ObjectMapper manifestObjectMapper;

    @Value("${manifest.location}")
    private String manifestLocation;

    @Override
    public List<Manifest> readManifestList() {

        List<File> manifestFilesList = getActualManifestNames();
        Assert.notEmpty(manifestFilesList, "No one manifest found at " + manifestLocation);

        log.debug("Found {} manifest candidates", manifestFilesList.size());

        List<Manifest> manifestList = manifestFilesList.stream()
                .map(this::deserializeFileToManifestModelOrNull)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        log.debug("Successfully read {} manifests", manifestList.size());

        return manifestList;
    }

    private Manifest deserializeFileToManifestModelOrNull(File file) {
        if (file.isDirectory()) {
            log.warn("Directories not supported by application, see FileManifestReader class. Skipping directory {}",
                    file.getAbsolutePath());
            return null;
        }

        if (!file.canRead()) {
            log.warn("Can't open file for reading. Skipping manifest {}", file.getAbsolutePath());
            return null;
        }

        try {
            log.debug("Read manifest {}", file.getAbsolutePath());
            return manifestObjectMapper.readValue(file, ManifestModel.class);
        } catch (IOException e) {
            ManifestReadException.throwError("Can't read manifest " + file.getAbsolutePath() + ", nested exception: ",
                    e);
            return null;
        }

    }

    private List<File> getActualManifestNames() {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        try {
            return Arrays.stream(resolver.getResources(manifestLocation))
                    .map(LambdaUtils::resourceToFileConversion)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            ManifestReadException.throwError("Can't read manifest list, nested exception: ", e);
            return null;
        }
    }

}
