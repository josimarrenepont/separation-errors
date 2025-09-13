package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.services.EmployeeService;
import com.backend.com.backend.services.impl.EmployeeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeServiceImpl employeeServiceImpl;

    public EmployeeController(EmployeeService employeeService, EmployeeServiceImpl employeeServiceImpl) {
        this.employeeService = employeeService;
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> list = employeeServiceImpl.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Employee obj = employeeServiceImpl.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/findByName/{name}")
    public ResponseEntity<Employee> findByName(@PathVariable String name) {
        Employee employee = employeeServiceImpl.findByName(name);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/totals")
    public Map<String, Integer> getEmployeeErrorTotals(@PathVariable Long id){
        Employee employee = employeeService.findById(id);

        if(employee == null){
            return Map.of();
        }
        Integer totPcMais = employeeService.calculateTotalError(employee, Separation::getErrorPcMais);
        Integer totPcMenos = employeeService.calculateTotalError(employee, Separation::getErrorPcMenos);
        Integer totPcErrada = employeeService.calculateTotalError(employee, Separation::getErrorPcErrada);

        return Map.of(
                "totPcMais", totPcMais,
                "totPcMenos", totPcMenos,
                "totPcErrada", totPcErrada
        );
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@RequestBody Employee obj) {
        obj = employeeServiceImpl.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}