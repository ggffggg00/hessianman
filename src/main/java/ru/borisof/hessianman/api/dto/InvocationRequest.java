package ru.borisof.hessianman.api.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.borisof.hessianman.utils.MapValueRawJsonDeserializer;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvocationRequest {

    private String serviceName;
    private String url;
    private String username;
    private String password;
    private String interfaceName;
    private String methodName;

    @JsonDeserialize(using = MapValueRawJsonDeserializer.class)
    private Map<String, Object> methodArgs;

}
