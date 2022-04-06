package ru.borisof.hessianman.hessian;

import lombok.Getter;
import org.springframework.util.Assert;
import ru.borisof.hessianman.loader.model.Interface;
import ru.borisof.hessianman.loader.model.MethodDeclaration;

import java.util.HashMap;
import java.util.Map;

//TODO Нехуй делать валидации в билдере, надо вынести в сервис
public class HessianRequestBuilder {

    @Getter
    private final String applicationName;

    @Getter
    private final String interfaceName;

    @Getter
    private final MethodDeclaration methodDeclaration;

    @Getter
    private final Object[] parameters;
    private final Map<String, Integer> nameToParamIndexMap;
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private String url;
    private boolean isMutable = true;

    private HessianRequestBuilder(final String anInterface,
                                  final String applicationName, final MethodDeclaration methodDeclaration) {
        this.interfaceName = anInterface;
        this.applicationName = applicationName;
        this.methodDeclaration = methodDeclaration;
        this.parameters = new Object[methodDeclaration.getMethodParameters().length];
        this.nameToParamIndexMap = new HashMap<>();

        for (int i = 0; i < methodDeclaration.getMethodParameters().length; i++) {
            nameToParamIndexMap.put(methodDeclaration.getMethodParameters()[i].getName(), i);
        }
    }

    public static HessianRequestBuilder getInstance(final Interface anInterface,
                                                    final MethodDeclaration methodDeclaration) {
        //TODO Исправить на нормальный Exception
        Assert.isTrue(anInterface.getMethodsDeclaration().contains(methodDeclaration), "Method not found in interface"
                                                                                       + " declaration");
        return new HessianRequestBuilder(anInterface.getSimpleName(), anInterface.getApplicationName(),
                methodDeclaration);
    }

    public HessianRequestBuilder parameter(String parameterKey, Object parameterValue) {
        Assert.isTrue(isMutable, "Hessian request already created, using same instance HessianRequestBuilder after "
                                 + "build() method call not allowed");

        Assert.notNull(parameterKey, "Parameter name can't be null");
        Assert.notNull(parameterValue, "Parameter value can't be null");

        Assert.isTrue(nameToParamIndexMap.containsKey(parameterKey),
                "Parameter with name" + parameterKey + " is not present");
        int paramIndex = nameToParamIndexMap.get(parameterKey);
        Class<?> parameterClass = methodDeclaration.getMethodParameters()[paramIndex].getArgumentType();

        Assert.isTrue(parameterClass.isInstance(parameterValue),
                "Parameter value must be same type with required method type. "
                + "Expected: " + parameterClass.getName() + ", actual: " + parameterValue.getClass().getName());

        parameters[paramIndex] = parameterValue;
        return this;
    }

    public HessianRequestBuilder username(String username) {
        Assert.isTrue(isMutable, "Hessian request already created, using same instance HessianRequestBuilder after "
                                 + "build() method call not allowed");
        this.username = username;
        return this;
    }

    public HessianRequestBuilder password(String password) {
        Assert.isTrue(isMutable, "Hessian request already created, using same instance HessianRequestBuilder after "
                                 + "build() method call not allowed");
        this.password = password;
        return this;
    }

    public HessianRequestBuilder url(String url) {
        Assert.isTrue(isMutable, "Hessian request already created, using same instance HessianRequestBuilder after "
                                 + "build() method call not allowed");
        this.url = url;
        return this;
    }

    public HessianRequest build() {
        isMutable = false;
        return new HessianRequest(
                this.applicationName,
                this.interfaceName,
                this.methodDeclaration,
                this.parameters,
                this.username,
                this.password, url);
    }
}
