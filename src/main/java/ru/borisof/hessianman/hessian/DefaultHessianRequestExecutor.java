package ru.borisof.hessianman.hessian;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.borisof.hessianman.loader.model.MethodDeclaration;
import ru.borisof.hessianman.loader.model.MethodParameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static ru.borisof.hessianman.utils.ExceptionUtils.getRecursiveCauseMessages;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultHessianRequestExecutor implements HessianRequestExecutor {

    private final HessianServiceProxy hessianServiceProxy;

    @Override
    public HessianRequestResult executeRequest(final HessianRequest req) {
        Assert.notNull(req.getInterfaceName(), "Target interface in request can't be null");
        Assert.notNull(req.getMethodDeclaration(), "Method to invoke can;t be null");

        //TODO Сделать валидацию параметров метода перед исполнением
        Object serviceProxyInstance = hessianServiceProxy.getServiceProxy(req.getApplicationName(),
                req.getInterfaceName(),
                req.getUrl(),
                req.getUsername(),
                req.getPassword());

        Class<?> serviceType = serviceProxyInstance.getClass();
        Method method = getReflectMethod(serviceType, req.getMethodDeclaration());

        HessianRequestResult result = executeRequest(serviceProxyInstance, method, req.getParameters());

        return result;
    }

    private HessianRequestResult executeRequest(Object instance, Method method2Invocation, Object[] params) {
        Object res = null;
        Exception exception = null;
        try {
            res = method2Invocation.invoke(instance, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            exception = e;
            e.printStackTrace();
        }

        if (res == null) {
            return HessianRequestResult.builder()
                    .errorMessage(getRecursiveCauseMessages(exception, 3))
                    .isSuccess(false)
                    .returnType(method2Invocation.getReturnType())
                    .build();
        }

        return HessianRequestResult.builder()
                .isSuccess(true)
                .response(res)
                .returnType(method2Invocation.getReturnType())
                .build();
    }

    //TODO Нормально обработать ошибку
    @SneakyThrows
    private Method getReflectMethod(Class<?> instanceClass, MethodDeclaration methodDeclaration) {
        Class<?>[] parameterTypes = Arrays.stream(methodDeclaration.getMethodParameters())
                .map(MethodParameter::getArgumentType)
                .toArray(Class<?>[]::new);
        return instanceClass.getDeclaredMethod(methodDeclaration.getName(), parameterTypes);

    }

}
