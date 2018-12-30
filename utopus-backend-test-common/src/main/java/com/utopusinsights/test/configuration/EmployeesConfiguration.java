package com.utopusinsights.test.configuration;

import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.entity.Input;
import com.utopusinsights.test.util.EmployeeUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Set;

/**
 * Bean definition of the list of Employees
 */
@Configuration
public class EmployeesConfiguration {

    private Input input;

    /**
     * Construct a new instance
     *
     * @param input the input to use
     */
    public EmployeesConfiguration(Input input) {
        this.input = input;
    }

    /**
     * Bean definition of the set of Employees
     *
     * @return the set of employees
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Set<Employee> employees() {
        return EmployeeUtil.buildEmployeeSetFromInput(input);
    }

}
