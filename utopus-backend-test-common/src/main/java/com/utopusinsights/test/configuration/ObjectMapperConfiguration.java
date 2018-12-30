package com.utopusinsights.test.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.util.EmployeeDeserializer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Bean definition of the custom ObjectMapper used to deserialize the input
 */
@Configuration
public class ObjectMapperConfiguration {

    private final EmployeeDeserializer employeeDeserializer;

    /**
     * Construct a new instance
     *
     * @param employeeDeserializer the EmployeeDeserializer instance to use
     */
    public ObjectMapperConfiguration(EmployeeDeserializer employeeDeserializer) {
        this.employeeDeserializer = employeeDeserializer;
    }

    /**
     * @return The custom ObjectMapper instance configured to use EmployeeDeserializer
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ObjectMapper objectMapper(){
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Employee.class, employeeDeserializer);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
