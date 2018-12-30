package com.utopusinsights.test.util;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Department-related utility methods
 */
public final class DepartmentUtil {

    /**
     * Add an employee to the list of employees mapped to Departments
     *
     * @param employeesOfDepartments lists of employees mapped to Departments
     * @param employee the employee to add
     */
    public static void addEmployeeToMap(Map<Department, List<Employee>> employeesOfDepartments, Employee employee) {
        employee.getDepartments().forEach(department -> {
            if (!employeesOfDepartments.containsKey(department)) {
                employeesOfDepartments.put(department, new LinkedList<>());
            }

            employeesOfDepartments.get(department).add(employee);
        });
    }

    /**
     * Combine two maps containing employee lists mapped to Departments. The entries in the second map get merged with
     * the entries in the first one. Then the first map is returned.
     *
     * @param departments1 the map to merge into
     * @param departments2 the map containing the entries to merge into the first one
     * @return the first map merged with the second one
     */
    public static Map<Department, List<Employee>> combineEmployeesOfDepartments(Map<Department, List<Employee>> departments1,
                                                                                Map<Department, List<Employee>> departments2) {
        synchronized (departments2) {
            departments2.forEach((department, employees) -> departments1.merge(department, employees, DepartmentUtil::concatenateLists));
        }
        return departments1;
    }

    /**
     * Transform a map containing lists of employees mapped to Departments to a list of Departments.
     *
     * The Departments are getting sorted by name
     *
     * @param employeesByDepartment a map containing lists of employees mapped to Departments
     * @return the list of Departments
     */
    public static List<Department> createSortedDepartmentList(Map<Department, List<Employee>> employeesByDepartment) {
        return employeesByDepartment
                .entrySet()
                .parallelStream()
                .map(DepartmentUtil::transformMapEntryToDepartment)
                .sorted(Comparator.comparing(Department::getName))
                .collect(Collectors.toList());
    }

    /**
     * Transform an entry of a map containing lists of employees mapped to Departments to a Department object.
     *
     * The members of the Departments are getting sorted by name
     *
     * @param employeesByDepartment the map entry to transform
     * @return the Department object
     */
    public static Department transformMapEntryToDepartment(Map.Entry<Department, List<Employee>> employeesByDepartment) {
        Department department = employeesByDepartment.getKey();
        List<Employee> employees = employeesByDepartment.getValue();

        Collections.sort(employees, Comparator.comparing(Employee::getFullName));
        department.setEmployees(employees);

        return department;
    }

    /**
     * Concatenate a list to an other one
     *
     * @param list the list to concatenate to
     * @param toConcatenate the list to concatenate to the first one
     * @return the first list extended with the second one
     */
    private static <T> List<T> concatenateLists(List<T> list, List<T> toConcatenate) {
        list.addAll(toConcatenate);
        return list;
    }

    /**
     * Create a list of Departments containing member employees
     *
     * @param employees the collection of employees
     * @return the list of Departments with member employees
     */
    public static List<Department> buildListOfDepartments(Collection<Employee> employees) {
        List<Department> departments = employees
                .parallelStream()
                .collect(Collector.of(
                        () -> Collections.synchronizedMap(new HashMap<>()),
                        DepartmentUtil::addEmployeeToMap,
                        DepartmentUtil::combineEmployeesOfDepartments,
                        DepartmentUtil::createSortedDepartmentList)
                );

        return departments;
    }

    /**
     * This is a utility class, do not instantiate
     */
    private DepartmentUtil() {
    }
}
