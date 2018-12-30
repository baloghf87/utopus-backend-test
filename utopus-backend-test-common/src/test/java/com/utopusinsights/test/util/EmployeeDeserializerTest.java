package com.utopusinsights.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopusinsights.test.configuration.ObjectMapperConfiguration;
import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmployeeDeserializerTest {

    private ObjectMapper objectMapper;

    public EmployeeDeserializerTest() {
        this.objectMapper = new ObjectMapperConfiguration(new EmployeeDeserializer()).objectMapper();
    }

    @Test
    public void parseValidName() throws IOException {
        String input = "{\"name\":\"Pal Tot\", \"department\": \"IT\"}";

        Employee employee = objectMapper.readValue(input, Employee.class);
        assertEquals("Pal", employee.getFirstName());
        assertEquals("Tot", employee.getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnInvalidName() throws IOException {
        String input = "{\"name\":\"Pal\", \"department\": \"IT\"}";

        objectMapper.readValue(input, Employee.class);
    }

    @Test
    public void parseDepartmentString() throws IOException {
        String input = "{\"name\":\"Pal Tot\", \"department\": \"IT\"}";

        Employee employee = objectMapper.readValue(input, Employee.class);
        assertEquals(new HashSet<>(Arrays.asList(new Department("IT"))), employee.getDepartments());
    }

    @Test
    public void parseDepartmentList() throws IOException {
        String input = "{\"name\":\"Pal Tot\", \"department\": [\"IT\", \"Finance\"]}";

        Employee employee = objectMapper.readValue(input, Employee.class);
        assertEquals(new HashSet<>(Arrays.asList(new Department("IT"), new Department("Finance"))), employee.getDepartments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNameIsMissing() throws IOException {
        String input = "{\"department\": [\"IT\", \"Finance\"]}";

        objectMapper.readValue(input, Employee.class);
    }

    @Test
    public void returnEmptyDepartmentsWhenWhenDepartmentIsMissing() throws IOException {
        String input = "{\"name\":\"Pal Tot\"}";

        Employee employee = objectMapper.readValue(input, Employee.class);
        assertTrue(employee.getDepartments().isEmpty());
    }
}
