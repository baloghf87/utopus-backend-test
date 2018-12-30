package com.utopusinsights.test;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    private List<Department> departments;

    public ConsoleApplication(List<Department> departments) {
        this.departments = departments;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        printEmployeesByDepartments(departments);
    }

    /**
     * Print Employees of Departments grouped by Departments
     *
     * @param departments the list of Departments
     */
    private void printEmployeesByDepartments(List<Department> departments) {
        departments.forEach(department -> {
            System.out.println(String.format("--- %s ---", department.getName()));
            department.getEmployees().stream().map(Employee::getFullName).forEach(System.out::println);
            System.out.println();
        });
    }
}