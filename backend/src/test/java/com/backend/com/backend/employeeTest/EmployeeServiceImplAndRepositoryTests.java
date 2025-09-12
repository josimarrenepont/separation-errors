package com.backend.com.backend.employeeTest;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.repositories.EmployeeRepository;
import com.backend.com.backend.services.impl.EmployeeServiceImpl;
import com.backend.com.backend.services.exceptions.DatabaseException;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplAndRepositoryTests {

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        employee = new Employee(1L, "Joao", "Empresa1", 3, 7, 7);
    }
    @Test
    public void testFindAll(){
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> result = employeeServiceImpl.findAll();

        assertEquals(1, result.size());
        assertEquals(employee, result.get(0));
    }
    @Test
    public void testFindById(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeServiceImpl.findById(1L);

        assertEquals(employee, result);
    }
    @Test
    public void testFindById_NotFound(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.findById(1L));
    }
    @Test
    public void testSave(){
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeRepository.save(employee);

        assertEquals(employee, result);
    }
    @Test
    public void testUpdateEmployee(){
        Employee updateEmployee = new Employee(1L, "Joao", "Empresa1", 7, 3, 3);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(updateEmployee);

        Employee result = employeeServiceImpl.update(1L, updateEmployee);

        assertEquals(updateEmployee, result);
    }
    @Test
    public void testDeleteExistingEmployee(){
        Long employeeId = 1L;
        doNothing().when(employeeRepository).deleteById(employeeId);
        assertDoesNotThrow(() -> employeeServiceImpl.delete(employeeId));
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
    @Test
    public void testDeleteNonExistingEmployee(){
        Long employeeId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(employeeRepository).deleteById(employeeId);
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.delete(employeeId));
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
    @Test
    public void testDeleteEmployeeWithDatabaseException(){
        Long employeeId = 1L;
        doThrow(new DataIntegrityViolationException("Integrity Violation")).when(employeeRepository).deleteById(employeeId);
        assertThrows(DatabaseException.class, () -> employeeServiceImpl.delete(employeeId));
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
    @Test
    public void testFindByName(){
        when(employeeRepository.findByName("Joao")).thenReturn(employee);

        Employee result = employeeServiceImpl.findByName("Joao");

        assertEquals(employee, result);
        verify(employeeRepository, times(1)).findByName("Joao");
    }
    @Test
    public void testGetEmployeeId(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeServiceImpl.getEmployeeById(1L);

        assertEquals(employee, result);
        verify(employeeRepository, times(1)).findById(1L);
    }
    @Test
    public void testGetEmployeeId_NotFound(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee result = employeeServiceImpl.getEmployeeById(1L);

        assertNull(result);
        verify(employeeRepository, times(1)).findById(1L);
    }
    @Test
    public void testAddErrorToEmployee() {

        Long employeeId = 1L;
        Separation error = new Separation();
        Employee employee = new Employee();
        Employee updatedEmployee = new Employee();
        updatedEmployee.addError(error);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeServiceImpl.addErrorToEmployee(employeeId, error);

        assertEquals(updatedEmployee, result);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(employee);
    }
    @Test
    public void testAddErrorToEmployee_NotFound() {

        Long employeeId = 1L;
        Separation error = new Separation();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            employeeServiceImpl.addErrorToEmployee(employeeId, error);
        });

        assertEquals("Funcionário não encontrado com o ID: " + employeeId, exception.getMessage());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}

