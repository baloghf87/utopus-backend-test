package com.utopusinsights.test.web;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RestEndpointsControllerTest {

    @Test
    public void testSort() {
        //given
        Employee employee1 = new Employee("Pal", "Tot");
        Employee employee2 = new Employee("Nora", "Kovacs");
        Employee employee3 = new Employee("Zsolt", "Nagy");
        Employee employee4 = new Employee("Anna", "Kis");

        Set<Employee> employees = new HashSet<>(Arrays.asList(employee1, employee2, employee3, employee4));

        RestEndpointsController restEndpointsController = new RestEndpointsController(null, employees);

        //when
        ResponseEntity response = restEndpointsController.names();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("KIS, Anna", "KOVACS, Nora", "NAGY, Zsolt", "TOT, Pal"), response.getBody());
    }

    @Test
    public void testDepartments() {
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");

        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance)));
        Employee employee2 = new Employee("Nora", "Kovacs", new HashSet<>(Arrays.asList(finance)));
        Employee employee3 = new Employee("Zsolt", "Nagy", new HashSet<>(Arrays.asList(qa)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

        qa.setEmployees(Arrays.asList(employee1, employee3));
        finance.setEmployees(Arrays.asList(employee1, employee2, employee4));

        RestEndpointsController restEndpointsController = new RestEndpointsController(
                Arrays.asList(qa, finance),
                new HashSet<>(Arrays.asList(employee1, employee2, employee3, employee4)));

        //when
        ResponseEntity response = restEndpointsController.departments("Pal", "Tot");

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(finance.getName(), qa.getName()), response.getBody());
    }

    @Test
    public void testDepartmentsNotFound() {
        //given
        RestEndpointsController restEndpointsController = new RestEndpointsController(Collections.emptyList(), Collections.emptySet());

        //when
        ResponseEntity response = restEndpointsController.departments("Some", "One");

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testEmployees(){
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");

        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance)));
        Employee employee2 = new Employee("Nora", "Kovacs", new HashSet<>(Arrays.asList(finance)));
        Employee employee3 = new Employee("Zsolt", "Nagy", new HashSet<>(Arrays.asList(qa)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

        qa.setEmployees(Arrays.asList(employee1, employee3));
        finance.setEmployees(Arrays.asList(employee1, employee2, employee4));

        RestEndpointsController restEndpointsController = new RestEndpointsController(
                Arrays.asList(qa, finance),
                new HashSet<>(Arrays.asList(employee1, employee2, employee3, employee4)));

        //when
        ResponseEntity response = restEndpointsController.employees(finance.getName());

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("KIS, Anna", "KOVACS, Nora", "TOT, Pal"), response.getBody());
    }

    @Test
    public void testEmployeesNotFound(){
        //given
        RestEndpointsController restEndpointsController = new RestEndpointsController(Collections.emptyList(), Collections.emptySet());

        //when
        ResponseEntity response = restEndpointsController.employees("Something");

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
