package ru.borisof.hessianman.loader;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.borisof.hessianman.loader.event.InterfaceLoadedEvent;
import ru.borisof.hessianman.loader.model.Interface;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SimpleInterfaceHolderService implements InterfaceHolderService {

    private Map<String, Interface> storage;

    @PostConstruct
    public void init() {
        if (this.storage == null) {
            this.storage = new ConcurrentHashMap<>();
        }
    }

    @EventListener
    public void onInterfaceLoadedEvent(InterfaceLoadedEvent interfaceLoadedEvent) {
        if (this.storage == null) {
            init();
        }
        storage.put(getKey(interfaceLoadedEvent.getApplicationName(),
                        interfaceLoadedEvent.getInterfaceModel().getSimpleName()),
                interfaceLoadedEvent.getInterfaceModel());
    }

    @Override
    public Interface getInterface(final String applicationName, final String interfaceSimpleName) {
        return storage.get(getKey(applicationName, interfaceSimpleName));
    }

    @Override
    public Collection<Interface> getAllApplicationInterfaces(final String applicationName) {
        return storage.entrySet().stream()
                .filter(el -> el.getKey().startsWith(applicationName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private String getKey(String applicationName, String interfaceName) {
        return applicationName + ":" + interfaceName;
    }

}
