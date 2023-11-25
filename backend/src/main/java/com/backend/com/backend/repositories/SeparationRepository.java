package com.backend.com.backend.repositories;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SeparationRepository extends JpaRepository<Separation, Long> {
	@Query(value= "SELECT obj FROM Separation obj JOIN FETCH obj.employees")
	List<Separation> searchAll();

	@Query
	List<Separation> findByDateBetween(Date startDate, Date endDate);
	@Query
	List<Employee> findByName(String name);

	@Query("SELECT s.errorPcMais FROM Separation s WHERE s.id = :separationId")
	Integer findErrorPcMaisById(@Param("separationId") Long separationId);

	@Query("SELECT s.errorPcMenos FROM Separation s WHERE s.id = :separationId")
	Integer findErrorPcMenosById(@Param("separationId") Long separationId);

	@Query("SELECT s.errorPcErrada FROM Separation s WHERE s.id = :separationId")
	Integer findErrorPcErradaById(@Param("separationId") Long separationId);


	@Modifying
	@Transactional
	@Query(nativeQuery = true,
			value= "UPDATE Separation s SET " +
			"s.errorPcMais = CASE WHEN s.errorPcMais <> 0 THEN s.errorPcMais ELSE :errorPcMais END, " +
			"s.errorPcMenos = CASE WHEN s.errorPcMenos <> 0 THEN s.errorPcMenos ELSE :errorPcMenos END, " +
			"s.errorPcErrada = CASE WHEN s.errorPcErrada <> 0 THEN s.errorPcErrada ELSE :errorPcErrada END " +
			"WHERE AND s.errorPcMais = :errorPcMais AND s.errorPcMenos = :errorPcMenos AND s.errorPcErrada = :errorPcErrada ")
	void updateColumnsThatWerentZeroed(@Param("errorPcMais") Integer errorPcMais,
									   @Param("errorPcMenos") Integer errorPcMenos,
									   @Param("errorPcErrada") Integer errorPcErrada);

	Separation getReferenceById(Long id);

}
