package com.utopusinsights.test.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.entity.Input;
import com.utopusinsights.test.util.EmployeeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;

/**
 * Bean definition of parsed Input
 */
@Configuration
public class InputConfiguration {

    private final String inputFilePath;
    private final ObjectMapper objectMapper;

    /**
     * Construct a new instance and parse the input file
     *
     * @param inputFilePath the path of the input file
     * @param objectMapper  the ObjectMapper instance to use for parsing the input file
     * @throws IOException
     */
    public InputConfiguration(@Value("${data.path}") String inputFilePath, ObjectMapper objectMapper) {
        this.inputFilePath = inputFilePath;
        this.objectMapper = objectMapper;
    }

    /**
     * Bean definition of the parsed input
     *
     * @return the parsed input
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Input input() throws IOException {
        return objectMapper.readValue(new FileReader(inputFilePath), Input.class);
    }

}
