package com.utopusinsights.test.configuration;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.util.DepartmentUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Set;

/**
 * Bean definition of the list of Departments
 */
@Configuration
public class DepartmentsConfiguration {

    private Set<Employee> employees;

    /**
     * Construct a new instance
     *
     * @param employees the set of employees
     */
    public DepartmentsConfiguration(Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Bean definition of the list of Departments
     *
     * @return the list of Departments ordered by name
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public List<Department> departments() {
        return DepartmentUtil.buildListOfDepartments(employees);
    }

}
