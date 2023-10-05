package com.backend.com.backend.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.backend.com.backend.entities.Separation;

public interface SeparationRepository extends JpaRepository<Separation, Long> {
	@Query(value= "SELECT obj FROM Separation obj JOIN FETCH obj.employees")
	List<Separation> searchAll();

	List<Separation> findByDateBetween(Date startDate, Date endDate);
}