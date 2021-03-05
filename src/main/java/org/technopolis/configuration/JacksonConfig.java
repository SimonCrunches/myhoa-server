package org.technopolis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.Nonnull;

@Configuration
public class JacksonConfig {
    @Primary
    @Bean
    public ObjectMapper jacksonObjectMapper(@Nonnull final Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }

}
