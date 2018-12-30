package com.utopusinsights.test.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom deserializer for the Employee class
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmployeeDeserializer extends JsonDeserializer<Employee> {

    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.readValueAs(JsonNode.class);

        // parse first and last names
        JsonNode nameNode = rootNode.get("name");
        if (nameNode == null) {
            throw new IllegalArgumentException("Name should be present.");
        }

        String name = nameNode.asText();
        int indexOfFirstSpace = name.indexOf(" ");

        if (indexOfFirstSpace == -1) {
            throw new IllegalArgumentException("Name should consist of first and last name(s).");
        }

        String firstName = name.substring(0, indexOfFirstSpace);
        String lastName = name.substring(indexOfFirstSpace + 1);

        Employee employee = new Employee(firstName, lastName);

        // parse departments
        JsonNode departmentNode = rootNode.get("department");
        if (departmentNode != null) {
            Set<String> departmentNames = getDepartmentNames(departmentNode);
            Set<Department> departments = departmentNames.stream().map(Department::new).collect(Collectors.toSet());
            employee.setDepartments(departments);
        } else {
            employee.setDepartments(Collections.emptySet());
        }

        return employee;
    }

    /**
     * Parse departments
     *
     * Handle strings and lists of strings
     *
     * @param departmentNode the JSON node to parse
     * @return the set of department names found
     */
    private static Set<String> getDepartmentNames(JsonNode departmentNode) {
        Set<String> departmentNames = new HashSet<>();

        if (departmentNode.isArray()) {
            departmentNode.forEach(departmentItem -> departmentNames.add(departmentItem.asText()));
        } else {
            departmentNames.add(departmentNode.asText());
        }

        return departmentNames;
    }
}
