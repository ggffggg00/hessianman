package ru.borisof.hessianman.loader.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class JarCacheStartedEvent {
    private Collection<URL> jarInternalUrlList;

    public static JarCacheStartedEvent of(Collection<URL> jarInternalUrlList) {
        return new JarCacheStartedEvent(jarInternalUrlList);
    }

}
