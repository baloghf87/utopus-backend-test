package com.utopusinsights.test;

import com.utopusinsights.test.entity.Department;
import com.utopusinsights.test.entity.Employee;
import com.utopusinsights.test.entity.Input;
import com.utopusinsights.test.entity.InputLists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({WebApplication.class, WebApplicationTest.TestInputConfiguration.class})
public class WebApplicationTest {

    private String baseUrl;

    private RestTemplate restTemplate;

    public WebApplicationTest() {
        restTemplate = new RestTemplate();
    }

    @LocalServerPort
    public void setPort(int port) {
        baseUrl = "http://localhost:" + port;
    }

    @Configuration
    public static class TestInputConfiguration {

        @Bean
        @Primary
        public static Input input() {
            Department qa = new Department("QA");
            Department finance = new Department("Finance");

            Employee employee1 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(qa)));
            Employee employee2 = new Employee("Pal", "Tot", new HashSet<>(Arrays.asList(qa)));
            Employee employee3 = new Employee("Anna", "Kis", new HashSet<>(Arrays.asList(finance)));

            List<Employee> employees = Arrays.asList(employee1, employee2, employee3);

            Input input = new Input();
            InputLists inputLists = new InputLists();
            inputLists.setEmployees(employees);
            input.setLists(inputLists);

            return input;
        }
    }

    @Test
    public void testSort() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/employee/sort", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("KIS, Anna", "TOT, Pal"), response.getBody());
    }

    @Test
    public void testDepartments() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/employee/Anna/Kis/department", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("Finance", "QA"), response.getBody());
    }

    @Test
    public void testDepartmentsNotFound() {
        try {
            restTemplate.getForEntity(baseUrl + "/employee/Anna/Kiss/department", List.class);
        } catch (HttpStatusCodeException exception) {
            assertEquals(404, exception.getStatusCode().value());
            return;
        }

        fail("Should have been failed");
    }

    @Test
    public void testEmployees() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/department/QA/employees", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("KIS, Anna", "TOT, Pal"), response.getBody());
    }

    @Test
    public void testEmployeesNotFound() {
        try {
            restTemplate.getForEntity(baseUrl + "/department/IT/employees", List.class);
        } catch (HttpStatusCodeException exception) {
            assertEquals(404, exception.getStatusCode().value());
            return;
        }

        fail("Should have been failed");
    }
}
