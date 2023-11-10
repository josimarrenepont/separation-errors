package com.backend.com.backend.repositories;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SeparationRepository extends CrudRepository<Separation, Long> {
	@Query(value= "SELECT obj FROM Separation obj JOIN FETCH obj.employees")
	List<Separation> searchAll();

	@Query
	List<Separation> findByDateBetween(Date startDate, Date endDate);
	@Query
	List<Employee> findByName(String name);

	Separation save(SeparationRequestDTO separation);

	@Query("SELECT SUM(s.subTotPcMais) FROM Separation s WHERE s.date BETWEEN :startDate AND :endDate")
	Integer sumSubTotPcMaisByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(s.subTotPcMenos) FROM Separation s WHERE s.date BETWEEN :startDate AND :endDate")
	Integer sumSubTotPcMenosByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(s.subTotPcErrada) FROM Separation s WHERE s.date BETWEEN :startDate AND :endDate")
	Integer sumSubTotPcErradaByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Modifying
	@Transactional
	@Query("UPDATE Separation s SET s.pcMais = :pcMais, s.pcMenos = :pcMenos, s.errorPcMais = :errorPcMais")
	void updateTotals(@Param("pcMais") Integer pcMais, @Param("pcMenos") Integer pcMenos, @Param("errorPcMais") Integer errorPcMais);

	Separation getReferenceById(Long id);

}