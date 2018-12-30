package com.utopusinsights.test.web;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST endpoints controller
 */
@RestController
public class RestEndpointsController {
    private List<Department> departments;
    private Set<Employee> employees;

    /**
     * Create a new instance
     *
     * @param departments the list of departments
     * @param employees the set of employees
     */
    public RestEndpointsController(List<Department> departments, Set<Employee> employees) {
        this.departments = departments;
        this.employees = employees;
    }

    /**
     * Get employees sorted by name
     *
     * @return Employees sorted by name
     */
    @GetMapping("/employee/sort")
    public ResponseEntity names() {
        return ResponseEntity.ok(employees
                .stream()
                .map(Employee::getFullName)
                .sorted()
                .collect(Collectors.toList()));
    }

    /**
     * Get departments of an employee
     *
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @return departments of the employee
     */
    @GetMapping("/employee/{firstName}/{lastName}/department")
    public ResponseEntity departments(@NotNull @PathVariable("firstName") String firstName, @NotNull @PathVariable("lastName") String lastName) {
        Optional<Employee> optionalEmployee = employees
                .stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst();

        if (!optionalEmployee.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalEmployee.get()
                .getDepartments()
                .stream()
                .map(Department::getName)
                .sorted()
                .collect(Collectors.toList()));
    }

    /**
     * Get employees of a department
     *
     * @param name name of the department
     * @return employees of the department sorted by name
     */
    @GetMapping("/department/{name}/employees")
    public ResponseEntity employees(@NotNull @PathVariable("name") String name) {
        Optional<Department> optionalDepartment = departments
                .stream()
                .filter(department -> department.getName().equals(name))
                .findAny();

        if (!optionalDepartment.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalDepartment.get()
                .getEmployees()
                .stream()
                .map(Employee::getFullName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
