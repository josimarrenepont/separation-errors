package com.backend.com.backend.services;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;

import java.util.List;
import java.util.function.Function;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(Long id);

    Employee insert(Employee obj);

    void delete(Long id);

    Employee update(Long id, Employee obj);

    Employee findByName(String name);

    Employee addErrorToEmployee(Long employeeId, Separation errorData);

    Employee getEmployeeById(Long employeeId);

    Integer calculateTotalError(Employee employee, Function<Separation, Integer> errorExtractor);
}
