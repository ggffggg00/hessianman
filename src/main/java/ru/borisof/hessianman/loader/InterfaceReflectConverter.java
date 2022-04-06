package ru.borisof.hessianman.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.borisof.hessianman.loader.event.InterfaceLoadedEvent;
import ru.borisof.hessianman.loader.event.JarCacheStartedEvent;
import ru.borisof.hessianman.loader.model.Interface;
import ru.borisof.hessianman.loader.model.MethodDeclaration;
import ru.borisof.hessianman.loader.model.MethodParameter;
import ru.borisof.hessianman.manifest.Manifest;
import ru.borisof.hessianman.manifest.ManifestHolder;
import ru.borisof.hessianman.manifest.model.ServiceInterfaceDeclarationModel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InterfaceReflectConverter {

    private final ManifestHolder manifestHolder;
    private final InterfaceLoader interfaceLoader;
    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    @Order(11)
    public void onJarCacheStartedEvent(JarCacheStartedEvent jarCacheStartedEvent) {
        loadInterfaceData();
    }

    public void loadInterfaceData() {
        for (Manifest manifest : manifestHolder.getAllManifests()) {
            manifest.getInterfaces().stream()
                    .map(anInterface -> convertClassToInterface(
                            interfaceLoader.getClassForInterface(anInterface.getTypeClass()),
                            anInterface, manifest))
                    .forEach(anInterface ->
                            applicationEventPublisher.publishEvent(
                                    InterfaceLoadedEvent.of(manifest.getServiceName(), anInterface)));

        }

    }

    private Interface convertClassToInterface(Class<?> anInterface,
                                              ServiceInterfaceDeclarationModel interfaceDeclarationModel,
                                              Manifest applicationManifest) {
        return Interface.builder()
                .relativePath(
                        applicationManifest.getHessianRootPath() + "/" + interfaceDeclarationModel.getSubPath())
                .applicationName(applicationManifest.getServiceName())
                .simpleName(interfaceDeclarationModel.getInterfaceName())
                .className(interfaceDeclarationModel.getTypeClass())
                .methodsDeclaration(getMethodDeclarations(anInterface.getDeclaredMethods()))
                .build();

    }

    private Collection<MethodDeclaration> getMethodDeclarations(Method[] methods) {
        return Arrays.stream(methods)
                .map(method -> MethodDeclaration.builder()
                        .name(method.getName())
                        .returnType(method.getReturnType())
                        .methodParameters(getMethodParameters(method))
                        .build())
                .collect(Collectors.toList());
    }

    private MethodParameter[] getMethodParameters(Method method) {
        return Arrays.stream(method.getParameters())
                .map(param -> MethodParameter.builder()
                        .name(param.getName())
                        .argumentType(param.getType())
                        .build())
                .toArray(MethodParameter[]::new);
    }

}
