package com.backend.com.backend.repositories;

import java.util.Date;
import java.util.List;

import com.backend.com.backend.entities.Employee;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.backend.com.backend.entities.Separation;
import org.springframework.data.repository.query.Param;

public interface SeparationRepository extends JpaRepository<Separation, Long> {
	@Query(value= "SELECT obj FROM Separation obj JOIN FETCH obj.employees")
	List<Separation> searchAll();

	@Query
	List<Separation> findByDateBetween(Date startDate, Date endDate);
	@Query
	List<Employee> findByName(String name);

	public Separation save(Separation separation);


}