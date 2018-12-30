package com.utopusinsights.test.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The Employee entity
 */
public class Employee {
    /**
     * First name of the employee
     */
    private String firstName;

    /**
     * Last name of the employee
     */
    private String lastName;

    /**
     * The set of Departments the employee belongs to
     */
    private Set<Department> departments;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Set<Department> departments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departments = departments;
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Employee setDepartments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Employee addDepartments(Collection<? extends Department> departments) {
        if (this.departments == null) {
            this.departments = new HashSet<>();
        }

        this.departments.addAll(departments);

        return this;
    }

    public String getFullName() {
        return String.format("%s, %s", lastName.toUpperCase(), firstName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(departments, employee.departments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, departments);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departments=" + departments +
                '}';
    }
}
