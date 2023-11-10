package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> list = employeeService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Employee obj = employeeService.findById(id);
        return ResponseEntity.ok().body(obj);

    }

    @GetMapping(value = "/findByName/{name}")
    public ResponseEntity<Employee> findByName(@PathVariable String name) {
        Employee employee = employeeService.findByName(name);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@RequestBody Employee obj) {
        obj = employeeService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee obj) {
        obj = employeeService.update(id, obj);

        // Adicione aqui o trecho de código para calcular e atualizar as somas acumuladas
        Integer somaAcumuladaPcMais = calcularSomaAcumuladaPcMais();
        Integer somaAcumuladaPcMenos = calcularSomaAcumuladaPcMenos();
        Integer somaAcumuladaPcErrada = calcularSomaAcumuladaPcErrada();

        obj.setTotPcMais(somaAcumuladaPcMais);
        obj.setTotPcMenos(somaAcumuladaPcMenos);
        obj.setTotPcErrada(somaAcumuladaPcErrada);

        return ResponseEntity.ok().body(obj);
    }
    private Integer calcularSomaAcumuladaPcErrada() {
        List<Employee> employees = employeeService.findAll();
        int soma = 0;
        for(Employee employee : employees){
            soma += employee.getTotPcErrada();
        }
        return soma;
    }

    private Integer calcularSomaAcumuladaPcMenos() {
        List<Employee> employees = employeeService.findAll();
        int soma = 0;
        for(Employee employee : employees){
            soma += employee.getTotPcMenos();
        }
        return soma;
    }

    private Integer calcularSomaAcumuladaPcMais() {
        List<Employee> employees = employeeService.findAll();
        int soma = 0;
        for(Employee employee : employees){
            soma += employee.getTotPcMais();
        }
        return soma;
    }
}