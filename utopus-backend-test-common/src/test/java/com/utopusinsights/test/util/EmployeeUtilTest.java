package com.utopusinsights.test.util;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.entity.Input;
import com.utopusinsights.test.entity.InputLists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmployeeUtilTest {

    @Test
    public void combineEmployeeSetsWithoutCollision() {
        //given
        Employee employee1 = new Employee("Pal", "Tot");
        Employee employee2 = new Employee("Nora", "Kovacs");
        Employee employee3 = new Employee("Zsolt", "Nagy");
        Employee employee4 = new Employee("Anna", "Kis");

        Set<Employee> employeeSet1 = new HashSet<>(Arrays.asList(employee1, employee2));
        Set<Employee> employeeSet2 = new HashSet<>(Arrays.asList(employee3, employee4));

        //when
        Set<Employee> result = EmployeeUtil.combineEmployeeSets(employeeSet1, employeeSet2);

        //then
        assertTrue(result == employeeSet1);
        assertEquals(new HashSet<>(Arrays.asList(employee1, employee2, employee3, employee4)), employeeSet1);
    }

    @Test
    public void combineEmployeeSetsWithCollision() {
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");

        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa)));
        Employee employee2 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa)));
        Employee employee3 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

        Set<Employee> employeeSet1 = new HashSet<>(Arrays.asList(employee1, employee2));
        Set<Employee> employeeSet2 = new HashSet<>(Arrays.asList(employee3, employee4));

        //when
        Set<Employee> result = EmployeeUtil.combineEmployeeSets(employeeSet1, employeeSet2);

        //then
        assertTrue(result == employeeSet1);
        HashSet<Employee> expectedResult = new HashSet<>(Arrays.asList(
                new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance))),
                new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa, finance)))));
        assertEquals(expectedResult, employeeSet1);
    }

    @Test
    public void addOrMergeEmployeeWithoutCollision() {
        //given
        Employee employee1 = new Employee("Pal", "Tot");
        Employee employee2 = new Employee("Nora", "Kovacs");
        Employee employee3 = new Employee("Zsolt", "Nagy");

        List<Employee> employees = new LinkedList<>(Arrays.asList(employee1, employee2));

        //when
        EmployeeUtil.addOrMergeEmployee(employees, employee3);

        //then
        assertEquals(Arrays.asList(employee1, employee2, employee3), employees);
    }

    @Test
    public void addOrMergeEmployeeWithCollision() {
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");

        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa)));
        Employee employee2 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa)));
        Employee employee3 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance)));

        List<Employee> employees = new LinkedList<>(Arrays.asList(employee1, employee2));

        //when
        EmployeeUtil.addOrMergeEmployee(employees, employee3);

        //then
        List<Employee> expectedResult = Arrays.asList(
                new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance))),
                new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa))));
        assertEquals(expectedResult, employees);

    }

    @Test
    public void buildEmployeeSetFromInput() {
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");

        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa)));
        Employee employee2 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa)));
        Employee employee3 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4);

        Input input = new Input();
        InputLists inputLists = new InputLists();
        inputLists.setEmployees(employees);
        input.setLists(inputLists);

        //when
        Set<Employee> result = EmployeeUtil.buildEmployeeSetFromInput(input);

        //then
        Set<Employee> expectedResult = new HashSet<>(Arrays.asList(
                new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance))),
                new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa, finance))),
                new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance))),
                new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa, finance)))));

        assertEquals(expectedResult, result);
    }

    @Test
    public void findEmployeeByName() {
        //given
        Employee employee1 = new Employee("Pal", "Tot");
        Employee employee2 = new Employee("Nora", "Kovacs");
        Employee employee3 = new Employee("Zsolt", "Nagy");
        Employee employee4 = new Employee("Anna", "Kis");

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4);

        //when
        Optional<Employee> optionalEmployee = EmployeeUtil.findEmployeeByName(employees, new Employee("Anna", "Kis"));

        //then
        assertTrue(optionalEmployee.isPresent());
        assertTrue(optionalEmployee.get() == employee4);
    }
}
