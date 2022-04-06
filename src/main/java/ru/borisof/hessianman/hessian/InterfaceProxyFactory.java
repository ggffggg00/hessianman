package ru.borisof.hessianman.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.borisof.hessianman.loader.InterfaceHolderService;
import ru.borisof.hessianman.loader.InterfaceLoader;
import ru.borisof.hessianman.loader.model.Interface;
import ru.borisof.hessianman.manifest.Manifest;
import ru.borisof.hessianman.manifest.ManifestHolder;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.TreeMap;

import static ru.borisof.hessianman.utils.Common.getOrDefault;

@Component
@RequiredArgsConstructor
public class InterfaceProxyFactory implements HessianServiceProxy {

    private final InterfaceLoader interfaceLoader;
    private final InterfaceHolderService interfaceHolder;
    private final ManifestHolder manifestHolder;

    private HessianProxyFactory hessianProxyFactory;
    private Map<String, Object> interfaceCache;

    @PostConstruct
    public void init() {
        hessianProxyFactory = new HessianProxyFactory();
        interfaceCache = new TreeMap<>();
    }

    public Object getProxy(Interface anInterface, String urlString, String username, String password) {
        Thread.currentThread().setContextClassLoader(interfaceLoader.getClassLoader());
        Manifest serviceManifest = manifestHolder.getManifestByName(anInterface.getApplicationName());
        //TODO Сделать параметры урла, логина и пароля изменяемыми

        if (interfaceCache.containsKey(getKey(anInterface, urlString))) {
            return interfaceCache.get(getKey(anInterface, urlString));
        }

        Object proxy = createProxy(anInterface, urlString, username, password);
        interfaceCache.put(getKey(anInterface, urlString), proxy);
        return proxy;

    }

    private Object createProxy(Interface anInterface, String urlString, String username, String password) {
        hessianProxyFactory.setUser(username);
        hessianProxyFactory.setPassword(password);

        String path = concatenateUrl(urlString, anInterface.getRelativePath());
        Class<?> interfaceClass = interfaceLoader.getClassForInterface(anInterface);

        try {
            return hessianProxyFactory.create(interfaceClass, path, interfaceClass.getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    //TODO Сделать нормальный выброс ошибок
    @SneakyThrows
    private String concatenateUrl(String rootPath, String relativePath) {
        return rootPath + relativePath;
    }

    private String getKey(Interface anInterface, String url) {
        return anInterface.getApplicationName() + ":" + url;
    }

    @Override
    public Object getServiceProxy(final String applicationName, final String interfaceName, String url) {
        return getServiceProxy(applicationName, interfaceName, url, null, null);
    }

    @Override
    public Object getServiceProxy(final String applicationName, final String interfaceName, final String url, final String username, final String password) {
        Interface serviceInterfaceDeclaration = interfaceHolder.getInterface(applicationName, interfaceName);

        Manifest applicationManifest = manifestHolder.getManifestByName(applicationName);

        return getProxy(serviceInterfaceDeclaration, url,
                getOrDefault(username, applicationManifest.getServiceUsername()),
                getOrDefault(password, applicationManifest.getServicePassword()));
    }
}
