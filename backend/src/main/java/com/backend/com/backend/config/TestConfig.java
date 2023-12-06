package com.backend.config;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.User;
import com.backend.com.backend.repositories.EmployeeRepository;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Date;

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

      Employee emp1 = new Employee(null, "Gilvan", "wne", 20, 10 , 30);
      Employee emp2 = new Employee(null, "Josenilson", "wne", 20, 10 , 30);
      Employee emp3 = new Employee(null, "Jose", "wne", 20, 10 , 30);
      Employee emp4 = new Employee(null, "Eduardo", "wne", 20, 10 , 30);

      Separation sep1 = new Separation(null, new Date(), "Gilvan", 1, 2, 3, 5, 6);
      Separation sep2 = new Separation(null, new Date(), "Josenilson", 1, 2, 3, 5, 6);
      Separation sep3 = new Separation(null, new Date(), "Jose", 1, 2, 3, 5, 6);
      Separation sep4 = new Separation(null, new Date(), "Eduardo", 1, 2, 3, 5, 6);

      User user1 = new User(null, "wne", "123");
      User user2 = new User(null, "mb", "123");
      User user3 = new User(null, "wsp", "123");
      User user4 = new User(null, "wsul", "123");
      User user5 = new User(null, "lm", "123");
      User user6 = new User(null, "wco", "123");


      for (Employee employee : Arrays.asList(emp1, emp2, emp3, emp4)) {
          int totPcMais = 0;
          int totPcMenos = 0;
          int totPcErrada = 0;


          }


          employeeRepository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4));
          separationRepository.saveAll(Arrays.asList(sep1, sep2, sep3, sep4));
          userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6));

      }

}
