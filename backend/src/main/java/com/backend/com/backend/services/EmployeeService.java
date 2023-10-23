package com.backend.com.backend.services;

import java.util.List;
import java.util.Optional;

import com.backend.com.backend.entities.Separation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.repositories.EmployeeRepository;
import com.backend.com.backend.services.exceptions.DatabaseException;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        Optional<Employee> obj = employeeRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Employee insert(Employee obj) {
        return employeeRepository.save(obj);
    }

    public void delete(Long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Employee update(Long id, Employee obj) {
        Optional<Employee> optionalEntity = employeeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Employee entity = optionalEntity.get();
            updateData(entity, obj);
            return employeeRepository.save(entity);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Employee entity, Employee obj) {
        entity.setName(obj.getName());
        entity.setBranch(obj.getBranch());
    }

    public Employee findByName(String name) {
        // Implemente a lógica para buscar o funcionário pelo nome no seu repositório
        return employeeRepository.findByName(name);

    }

    public Employee addErrorToEmployee(Long employeeId, Separation errorData) {
        // Primeiro, você deve recuperar o funcionário pelo ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + employeeId));

        // Agora, você pode adicionar os erros ao funcionário
        // Suponha que a classe Employee tenha um método para adicionar erros, como
        // addError(Separation errorData)
        employee.addError(errorData);

        // Salve o funcionário atualizado no repositório
        Employee updatedEmployee = employeeRepository.save(employee);

        return updatedEmployee;
    }

}
