package com.utopusinsights.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wrapper class for the lists in the input file
 */
public class InputLists {
    @JsonProperty("employee")
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "InputList{" +
                "employees=" + employees +
                '}';
    }
}
