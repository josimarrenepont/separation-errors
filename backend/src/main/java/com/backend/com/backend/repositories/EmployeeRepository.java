package com.backend.com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.com.backend.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	static Employee findByName(String name) {
		
		return findByName(name);
	}

	

}