package com.backend.com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.com.backend.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByName(String name);

	Employee findEmployeeWithErrorsById(Long id);

	
	
	}


