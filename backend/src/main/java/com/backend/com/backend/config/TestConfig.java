package com.backend.com.backend.config;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.User;
import com.backend.com.backend.repositories.EmployeeRepository;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SeparationRepository separationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        Employee emp1 = new Employee(null, "Alexandre", "wne", null, null, null);
        Employee emp2 = new Employee(null, "Denison", "wne", null, null, null);
        Employee emp3 = new Employee(null, "Gilvan", "wne", null, null, null);
        Employee emp4 = new Employee(null, "Gildoberto", "wne", null, null, null);

        Separation sep1 = new Separation(null, new Date(), "Alexandre", 968, 123, 2, 3, 5, 1);
        Separation sep2 = new Separation(null, new Date(), "Denison", 997, 123, 5, 3, 2, 2);
        Separation sep3 = new Separation(null, new Date(), "Gilvan", 986, 123, 3, 7, 1, 6);
        Separation sep4 = new Separation(null, new Date(), "Gildoberto", 958, 123, 5, 9, 10, 7);

        User user1 = new User(null, "wne", "123");
        User user2 = new User(null, "mb", "123");
        User user3 = new User(null, "wsp", "123");
        User user4 = new User(null, "wsul", "123");
        User user5 = new User(null, "lm", "123");
        User user6 = new User(null, "wco", "123");

        emp1.getErrors().add(sep1);
        emp2.getErrors().add(sep2);
        emp3.getErrors().add(sep3);
        emp4.getErrors().add(sep4);

        for (Employee employee : Arrays.asList(emp1, emp2, emp3, emp4)) {
            int totPcMais = 0;
            int totPcMenos = 0;
            int totPcErrada = 0;

            for (Separation error : employee.getErrors()) {
                totPcMais += error.getSubTotPcMais();
                totPcMenos += error.getSubTotPcMenos();
                totPcErrada += error.getSubTotPcErrada();
            }
            employee.setTotPcMais(totPcMais);
            employee.setTotPcMenos(totPcMenos);
            employee.setTotPcErrada(totPcErrada);
            employeeRepository.save(employee);

            employeeRepository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4));
            separationRepository.saveAll(Arrays.asList(sep1, sep2, sep3, sep4));
            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6));

        }

    }

}