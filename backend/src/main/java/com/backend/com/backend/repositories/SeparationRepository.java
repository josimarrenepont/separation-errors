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

	@Query("SELECT s.pcMais FROM Separation s WHERE s.id = :separationId")
	Integer findPcMaisById(@Param("separationId") Long separationId);

	@Query("SELECT s.errorPcMais FROM Separation s WHERE s.id = :separationId")
	Integer findErrorPcMaisById(@Param("separationId") Long separationId);

	@Query("SELECT s.pcMenos FROM Separation s WHERE s.id = :separationId")
	Integer findPcMenosById(@Param("separationId") Long separationId);

	@Query("SELECT s.errorPcMenos FROM Separation s WHERE s.id = :separationId")
	Integer findErrorPcMenosById(@Param("separationId") Long separationId);

	@Query("SELECT s.pcErrada FROM Separation s WHERE s.id = :separationId")
	Integer findPcErradaById(@Param("separationId") Long separationId);

	@Query("SELECT s.errorPcErrada FROM Separation s WHERE s.id = :separationId")
	Integer findErrorPcErradaById(@Param("separationId") Long separationId);

	@Query("SELECT SUM(s.subTotPcMais) FROM Separation s WHERE s.date BETWEEN :startDate AND :endDate")
	Integer sumSubTotPcMaisByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(s.subTotPcMenos) FROM Separation s WHERE s.date BETWEEN :startDate AND :endDate")
	Integer sumSubTotPcMenosByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(s.subTotPcErrada) FROM Separation s WHERE s.date BETWEEN :startDate AND :endDate")
	Integer sumSubTotPcErradaByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Modifying
	@Transactional
	@Query(nativeQuery = true,
			value = "UPDATE Separation s SET s.date = :date, s.pallet = :pallet, s.codProduct = :codProduct, s.pcMais = :pcMais, " +
			"s.subTotPcMais = :subTotPcMais, s.subTotPcMenos = :subTotPcMenos, s.subTotPcErrada = :subTotPcErrada, " +
			"s.pcMenos = :pcMenos, s.pcErrada = :pcErrada, " +
			"s.errorPcMais = :errorPcMais, s.errorPcMenos = :errorPcMenos, " +
			"s.errorPcErrada = :errorPcErrada WHERE s.id = :id")
	void updateTotals(
			@Param("id") Long id, @Param("date") Date date, @Param("pcMais") Integer pcMais, @Param("pcMenos") Integer pcMenos, @Param("pcErrada") Integer pcErrada,
			@Param("errorPcMais") Integer errorPcMais, @Param("errorPcMenos") Integer errorPcMenos, @Param("errorPcErrada") Integer errorPcErrada,
			@Param("pallet") Integer pallet, @Param("codProduct") Integer codProduct,
			@Param("subTotPcMais") Integer subTotPcMais, @Param("subTotPcErrada") Integer subTotPcErrada,
			@Param("subTotPcMenos") Integer subTotPcMenos);

	@Modifying
	@Transactional
	@Query(nativeQuery = true,
			value= "UPDATE Separation s SET " +
			"s.pcMais = CASE WHEN s.pcMais <> 0 THEN s.pcMais ELSE :pcMais END, " +
			"s.subTotPcMais = CASE WHEN s.subTotPcMais <> 0 THEN s.subTotPcMais ELSE :subTotPcMais END, " +
			"s.subTotPcMenos = CASE WHEN s.subTotPcMenos <> 0 THEN s.subTotPcMenos ELSE :subTotPcMenos END, " +
			"s.subTotPcErrada = CASE WHEN s.subTotPcErrada <> 0 THEN s.subTotPcErrada ELSE :subTotPcErrada END, " +
			"s.pcMenos = CASE WHEN s.pcMenos <> 0 THEN s.pcMenos ELSE :pcMenos END, " +
			"s.pcErrada = CASE WHEN s.pcErrada <> 0 THEN s.pcErrada ELSE :pcErrada END, " +
			"s.errorPcMais = CASE WHEN s.errorPcMais <> 0 THEN s.errorPcMais ELSE :errorPcMais END, " +
			"s.errorPcMenos = CASE WHEN s.errorPcMenos <> 0 THEN s.errorPcMenos ELSE :errorPcMenos END, " +
			"s.errorPcErrada = CASE WHEN s.errorPcErrada <> 0 THEN s.errorPcErrada ELSE :errorPcErrada END " +
			"WHERE s.pcMais = :pcMais AND s.pcMenos = :pcMenos AND s.pcErrada = :pcErrada " +
			"AND s.errorPcMais = :errorPcMais AND s.errorPcMenos = :errorPcMenos AND s.errorPcErrada = :errorPcErrada " +
			"AND s.subTotPcMais = :subTotPcMais " +
			"AND s.subTotPcMenos = :subTotPcMenos AND s.subTotPcErrada = :subTotPcErrada")
	void updateColumnsThatWerentZeroed(@Param("pcMais") Integer pcMais, @Param("pcMenos") Integer pcMenos,
									   @Param("pcErrada") Integer pcErrada, @Param("errorPcMais") Integer errorPcMais,
									   @Param("errorPcMenos") Integer errorPcMenos, @Param("errorPcErrada") Integer errorPcErrada,
									   @Param("subTotPcMais") Integer subTotPcMais, @Param("subTotPcMenos") Integer subTotPcMenos,
									   @Param("subTotPcErrada") Integer subTotPcErrada);

	Separation getReferenceById(Long id);


}