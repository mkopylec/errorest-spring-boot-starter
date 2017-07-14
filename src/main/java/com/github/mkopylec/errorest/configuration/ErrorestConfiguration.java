package com.github.mkopylec.errorest.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mkopylec.errorest.handling.ControllerErrorHandler;
import com.github.mkopylec.errorest.handling.RequestMethodAttributeSettingFilter;
import com.github.mkopylec.errorest.handling.ServletFilterErrorHandler;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.logging.ErrorsLoggingList;
import com.github.mkopylec.errorest.logging.ExceptionLogger;
import com.github.mkopylec.errorest.response.Error;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.io.IOException;
import java.util.Map;

import static com.github.mkopylec.errorest.response.Errors.ERRORS_ITEM_FIELD;
import static com.github.mkopylec.errorest.response.Errors.ROOT_ERRORS_FIELD;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties({ErrorestProperties.class, ServerProperties.class})
public class ErrorestConfiguration {

    protected final ErrorestProperties errorestProperties;
    protected final ServerProperties serverProperties;

    public ErrorestConfiguration(ErrorestProperties errorestProperties, ServerProperties serverProperties) {
        this.errorestProperties = errorestProperties;
        this.serverProperties = serverProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ControllerErrorHandler controllerErrorHandler(ExceptionLogger exceptionLogger, ErrorDataProviderContext providerContext) {
        return new ControllerErrorHandler(errorestProperties, exceptionLogger, providerContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletFilterErrorHandler servletFilterErrorHandler(ErrorAttributes errorAttributes, ExceptionLogger logger, ErrorDataProviderContext providerContext) {
        return new ServletFilterErrorHandler(errorAttributes, serverProperties, errorestProperties, logger, providerContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMethodAttributeSettingFilter requestMethodAttributeSettingFilter() {
        return new RequestMethodAttributeSettingFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionLogger exceptionLogger() {
        return new ExceptionLogger();
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDataProviderContext errorDataProviderContext() {
        return new ErrorDataProviderContext(errorestProperties);
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(Jackson2ObjectMapperBuilder builder) {
        XmlMapper mapper = builder.createXmlMapper(true).build();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Map.class, new StdSerializer<Map>(Map.class) {

            @Override
            public void serialize(Map value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (value != null && value.size() == 1 && value.containsKey(ROOT_ERRORS_FIELD) && value.values().stream().findFirst().orElse(null) instanceof String) {
                    gen.writeStartObject();
                    gen.writeArrayFieldStart(ERRORS_ITEM_FIELD);
                    ErrorsLoggingList list = (ErrorsLoggingList) value.values().stream().findFirst().get();
                    for (Error error : list) {
                        gen.writeObject(error);
                    }
                    gen.writeEndArray();
                    gen.writeEndObject();
                }
                provider.findValueSerializer(Map.class, ).serialize(value, gen, provider);
            }
        });
        mapper.registerModule(module);
        SerializationConfig config = mapper.getSerializationConfig().withRootName(ROOT_ERRORS_FIELD);
        mapper.setConfig(config);
        return new MappingJackson2XmlHttpMessageConverter(mapper);
    }
}
