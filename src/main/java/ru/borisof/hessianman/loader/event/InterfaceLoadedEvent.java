package ru.borisof.hessianman.loader.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.borisof.hessianman.loader.model.Interface;

@Getter
@AllArgsConstructor
public class InterfaceLoadedEvent {

    private String applicationName;
    private Interface interfaceModel;

    public static InterfaceLoadedEvent of(String applicationName, Interface interfaceModel) {
        return new InterfaceLoadedEvent(applicationName, interfaceModel);
    }

}
