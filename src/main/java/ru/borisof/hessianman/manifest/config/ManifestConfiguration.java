package ru.borisof.hessianman.manifest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.borisof.hessianman.manifest.DefaultMapManifestHolder;
import ru.borisof.hessianman.manifest.ManifestHolder;
import ru.borisof.hessianman.manifest.ManifestReader;

@Configuration
public class ManifestConfiguration {


    @Bean
    public ObjectMapper manifestObjectMapper(){
        return new ObjectMapper();
    }

    @Order(1)
    @Bean
    public ManifestHolder manifestHolder(
            @Qualifier("defaultManifestReader") ManifestReader reader
    ) {
        DefaultMapManifestHolder defaultMapManifestHolder = new DefaultMapManifestHolder();
        defaultMapManifestHolder.addAll(reader.readManifestList());
        return defaultMapManifestHolder;

    }

}
