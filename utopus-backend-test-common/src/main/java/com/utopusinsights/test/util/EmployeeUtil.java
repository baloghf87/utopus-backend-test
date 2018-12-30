package com.utopusinsights.test.util;

import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.entity.Input;

import java.util.*;
import java.util.stream.Collector;

/**
 * Employee-related utility methods
 */
public final class EmployeeUtil {

    /**
     * Combine two sets of employees. Add the employees from the second set to the first one. If an employee is
     * already present, add the missing Departments.
     *
     * @param employees1 the set to merge into, this one will be returned
     * @param employees2 the set containing the employees to merge into the first one
     * @return the first set complemented with the employees in the second one
     */
    public static Set<Employee> combineEmployeeSets(Set<Employee> employees1, Set<Employee> employees2) {
        synchronized (employees2) {
            employees2.forEach(employee -> addOrMergeEmployee(employees1, employee));
        }
        return employees1;
    }

    /**
     * Add an employee to a collection of employees. If an employee with the same name already exists in the collection
     * then merge the Departments into the one already in the collection.
     *
     * @param employees the collection of employees
     * @param employee  the employee to add/merge into the collection
     */
    public static void addOrMergeEmployee(Collection<Employee> employees, Employee employee) {
        Optional<Employee> employeeOptional = findEmployeeByName(employees, employee);
        if (employeeOptional.isPresent()) {
            employeeOptional.get().addDepartments(employee.getDepartments());
        } else {
            employees.add(employee);
        }
    }

    /**
     * Find an employee by name in a collection
     *
     * @param employees the collection of employees
     * @param employee  the employee to find
     * @return the employee found (wrapped into an Optional)
     */
    public static Optional<Employee> findEmployeeByName(Collection<Employee> employees, Employee employee) {
        return employees
                .parallelStream()
                .filter(resultEmployee ->
                        resultEmployee.getFirstName().equals(employee.getFirstName()) &&
                                resultEmployee.getLastName().equals(employee.getLastName()))
                .findAny();
    }

    /**
     * Construct a set of Employees from raw input
     *
     * @param input the input containing the parsed raw data
     * @return the set of Employees
     */
    public static Set<Employee> buildEmployeeSetFromInput(Input input){
        Set<Employee> employees = input.getLists().getEmployees()
                .parallelStream()
                .collect(Collector.of(
                        () -> Collections.synchronizedSet(new HashSet<Employee>()),
                        EmployeeUtil::addOrMergeEmployee,
                        EmployeeUtil::combineEmployeeSets)
                );

        return employees;
    }

    /**
     * This is a utility class, do not instantiate
     */
    private EmployeeUtil() {
    }
}
