package com.utopusinsights.test.util;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.entity.Input;
import com.utopusinsights.test.entity.InputLists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DepartmentUtilTest {

    @Test
    public void addEmployeeToExistingListInMap() {
        //given
        Department department = new Department("QA");
        LinkedList<Employee> employeeList = new LinkedList<>();
        Employee employee = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(department)));

        Map<Department, List<Employee>> employeesOfDepartments = new HashMap<>();
        employeesOfDepartments.put(department, employeeList);

        //when
        DepartmentUtil.addEmployeeToMap(employeesOfDepartments, employee);

        //then
        assertTrue(employeesOfDepartments.get(department) == employeeList);
        assertTrue(employeeList.contains(employee));
    }

    @Test
    public void addEmployeeToNewListInMap() {
        //given
        Department department = new Department("QA");
        Employee employee = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(department)));

        Map<Department, List<Employee>> employeesOfDepartments = new HashMap<>();

        //when
        DepartmentUtil.addEmployeeToMap(employeesOfDepartments, employee);

        //then
        List<Employee> employeeList = employeesOfDepartments.get(department);
        assertNotNull(employeeList);
        assertTrue(employeeList.contains(employee));
    }

    @Test
    public void combineEmployeesOfDepartmentsWithoutCollision() {
        //given
        Department department1 = new Department("QA");
        Department department2 = new Department("Finance");
        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(department1)));
        Employee employee2 = new Employee("Andras", "Nagy", new HashSet<>(Arrays.asList(department2)));

        Map<Department, List<Employee>> employeesOfDepartments1 = new HashMap<>();
        List<Employee> employeeList1 = Arrays.asList(employee1);
        employeesOfDepartments1.put(department1, employeeList1);

        Map<Department, List<Employee>> employeesOfDepartments2 = new HashMap<>();
        List<Employee> employeeList2 = Arrays.asList(employee2);
        employeesOfDepartments2.put(department2, employeeList2);

        //when
        Map<Department, List<Employee>> result = DepartmentUtil.combineEmployeesOfDepartments(employeesOfDepartments1, employeesOfDepartments2);

        //then
        assertEquals(employeeList1, result.get(department1));
        assertEquals(employeeList2, result.get(department2));
    }

    @Test
    public void combineEmployeesOfDepartmentsWithCollision() {
        //given
        Department department = new Department("QA");
        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(department)));
        Employee employee2 = new Employee("Andras", "Nagy", new HashSet<>(Arrays.asList(department)));

        Map<Department, List<Employee>> employeesOfDepartments1 = new HashMap<>();
        List<Employee> employeeList1 = new ArrayList<>(Arrays.asList(employee1));
        employeesOfDepartments1.put(department, employeeList1);

        Map<Department, List<Employee>> employeesOfDepartments2 = new HashMap<>();
        List<Employee> employeeList2 = new ArrayList<>(Arrays.asList(employee2));
        employeesOfDepartments2.put(department, employeeList2);

        //when
        Map<Department, List<Employee>> result = DepartmentUtil.combineEmployeesOfDepartments(employeesOfDepartments1, employeesOfDepartments2);

        //then
        assertEquals(Arrays.asList(employee1, employee2), result.get(department));
    }

    @Test
    public void createSortedDepartmentList() {
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");
        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa)));
        Employee employee2 = new Employee("Nora", "Kovacs", new HashSet<>(Arrays.asList(finance)));
        Employee employee3 = new Employee("Zsolt", "Nagy", new HashSet<>(Arrays.asList(qa)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

        Map<Department, List<Employee>> employeesOfDepartments = new HashMap<>();
        employeesOfDepartments.put(qa, Arrays.asList(employee1, employee2));
        employeesOfDepartments.put(finance, Arrays.asList(employee3, employee4));

        //when
        List<Department> result = DepartmentUtil.createSortedDepartmentList(employeesOfDepartments);

        //then
        assertEquals(finance.getName(), result.get(0).getName());
        assertEquals(qa.getName(), result.get(1).getName());

        assertEquals(new LinkedHashSet<>(Arrays.asList(employee2, employee1)), qa.getEmployees());
        assertEquals(new LinkedHashSet<>(Arrays.asList(employee4, employee3)), finance.getEmployees());
    }

    @Test
    public void transformMapEntryToDepartment() {
        //given
        Department qa = new Department("QA");
        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa)));
        Employee employee2 = new Employee("Nora", "Kovacs", new HashSet<>(Arrays.asList(qa)));
        Employee employee3 = new Employee("Zsolt", "Nagy", new HashSet<>(Arrays.asList(qa)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa)));

        LinkedList<Employee> employeeList = new LinkedList<>(Arrays.asList(employee1, employee2, employee3, employee4));

        Map.Entry<Department, List<Employee>> employeesByDepartment = new HashMap.SimpleEntry<>(qa, employeeList);

        //when
        Department result = DepartmentUtil.transformMapEntryToDepartment(employeesByDepartment);

        //then
        assertEquals(qa, result);
        assertEquals(new LinkedHashSet<>(Arrays.asList(employee4, employee2, employee1, employee3)), qa.getEmployees());
    }

    @Test
    public void buildListOfDepartments() {
        //given
        Department qa = new Department("QA");
        Department finance = new Department("Finance");
        Employee employee1 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa, finance)));
        Employee employee2 = new Employee("Nora", "Kovacs", new HashSet<>(Arrays.asList(finance)));
        Employee employee3 = new Employee("Zsolt", "Nagy", new HashSet<>(Arrays.asList(qa)));
        Employee employee4 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4);

        //when
        List<Department> result = DepartmentUtil.buildListOfDepartments(employees);

        //then
        assertEquals(finance.getName(), result.get(0).getName());
        assertEquals(qa.getName(), result.get(1).getName());

        assertEquals(new LinkedHashSet<>(Arrays.asList(employee4, employee2, employee1)), finance.getEmployees());
        assertEquals(new LinkedHashSet<>(Arrays.asList(employee1, employee3)), qa.getEmployees());
    }

}
