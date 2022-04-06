package ru.borisof.hessianman.loader;


import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.borisof.hessianman.loader.event.JarCacheStartedEvent;
import ru.borisof.hessianman.loader.exception.StartingUpException;
import ru.borisof.hessianman.loader.model.Interface;

import java.net.URL;
import java.net.URLClassLoader;

@Service
public class LazyInitInterfaceLoader implements InterfaceLoader {

    private ClassLoader classLoader;

    @EventListener
    @Order(10)
    public void onJarCacheStartedEvent(JarCacheStartedEvent jarCacheStartedEvent) {
        URL[] jarUrls = jarCacheStartedEvent.getJarInternalUrlList().toArray(new URL[0]);
        classLoader = new URLClassLoader(jarUrls);
    }

    @Override
    public Class<?> getClassForInterface(final Interface anInterface) {
        if (classLoader == null)
            throw StartingUpException.defaultMessage();

        return getClassForInterface(anInterface.getClassName());
    }

    @Override
    public Class<?> getClassForInterface(final String interfaceName) {

        try {
            return Class.forName(interfaceName, true, classLoader);
        } catch (ClassNotFoundException e) {
            // TODO
            throw new RuntimeException("Unknown error");
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }


}
