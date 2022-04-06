package ru.borisof.hessianman.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.borisof.hessianman.loader.event.JarCacheStartedEvent;
import ru.borisof.hessianman.loader.exception.JarCacheException;
import ru.borisof.hessianman.manifest.Manifest;
import ru.borisof.hessianman.manifest.ManifestHolder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocalJarCache {

    public static final int CONNECT_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 60000;
    private final ManifestHolder manifestHolder;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${jar.include}")
    private List<String> additionalJarLibs;

    @Value("${jar.location}")
    private String jarLocation;

    @EventListener
    public void updateCacheAfterStart(ApplicationStartedEvent applicationStartedEvent) {
        Collection<URL> jarLocalUrls = manifestHolder.getAllManifests().stream()
                .map(Manifest::getInterfacesArtifactUrl)
                .map(this::saveAndGetJarLocalUrl)
                .collect(Collectors.toList());

        jarLocalUrls.addAll(additionalJarLibs.stream()
                .map(this::saveAndGetJarLocalUrl)
                .collect(Collectors.toList()));

        eventPublisher.publishEvent(JarCacheStartedEvent.of(jarLocalUrls));
    }

    public URL saveAndGetJarLocalUrl(String jarUrl) {
        try {
            URL url = new URL(jarUrl);
            File cachedFile = getTargetJarFilePath(url);

            if (!cachedFile.exists()) {
                log.debug("Jar file {} doesnt exists at cache, pulling it from {}", cachedFile.getName(), jarUrl);
                downloadFile(url, cachedFile);
            }

            return constructLocalJarUrl(cachedFile);

        } catch (IOException e) {
            throw JarCacheException.of("Error occurred while saving jar in cache", e);
        }

    }

    public Collection<URL> saveAllAndGetJarLocalUrlCollection(Collection<String> jarUrls) {
        return jarUrls.stream()
                .map(this::saveAndGetJarLocalUrl)
                .collect(Collectors.toList());
    }

    public URL getJarLocalUrl(String jarUrl) {
        try {
            File jarFile = getTargetJarFilePath(new URL(jarUrl));

            if (!jarFile.exists()) {
                return saveAndGetJarLocalUrl(jarUrl);
            }
            return constructLocalJarUrl(jarFile);
        } catch (IOException e) {
            throw JarCacheException.of("Error occurred while fetching jar from cache", e);
        }
    }

    private URL constructLocalJarUrl(File file) throws MalformedURLException {
        return new URL("jar:file:" + file.getAbsolutePath() + "!/");
    }

    private File getTargetJarFilePath(URL fileUrl) throws IOException {
        String fileName = Arrays.stream(fileUrl.getPath().split("/"))
                .filter(pathEl -> pathEl.endsWith(".jar"))
                .findFirst().orElseThrow(() -> JarCacheException.of("Wrong url to jar file. File must ends"
                                                                    + " with .jar extension"));

        Path rootPath = Paths.get(jarLocation);
        Path jarPath = rootPath.resolve(Paths.get(fileName));
        File targetFile = jarPath.toFile();

        if (!targetFile.getParentFile().exists()) {
            FileUtils.createParentDirectories(targetFile);
        }

        return targetFile;

    }

    private void downloadFile(URL fileUrl, File targetFile) throws IOException {
        FileUtils.copyURLToFile(
                fileUrl,
                targetFile,
                CONNECT_TIMEOUT,
                READ_TIMEOUT);
    }


}
