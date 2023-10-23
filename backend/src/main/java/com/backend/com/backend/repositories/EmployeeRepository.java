package com.backend.com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.com.backend.entities.Employee;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query
	Employee findByName(String name);

}
