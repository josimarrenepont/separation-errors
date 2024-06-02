package com.backend.com.backend.employeeTest;

import com.backend.com.backend.controllers.EmployeeController;
import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employee = new Employee(1l, "Joao", "Empresa1", 3, 7, 7);
    }
    @Test
    public void testFindAll() throws Exception {
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeService.findAll()).thenReturn(employeeList);

        ResultActions result = mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(employee.getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(employee.getName()));
     }
     @Test
    public void testFindById() throws Exception{
        when(employeeService.findById(1L)).thenReturn(employee);

        ResultActions result = mockMvc.perform(get("/employees/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id").value(employee.getId()))
                .andExpect((ResultMatcher) jsonPath("$.name").value(employee.getName()));
     }
     @Test
    public void testFindByName() throws Exception{
        when(employeeService.findByName("Joao")).thenReturn(employee);

        ResultActions result = mockMvc.perform(get("/employees/findByName/Joao")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id").value(employee.getId()))
                .andExpect((ResultMatcher) jsonPath("$.name").value(employee.getName()));
     }
     @Test
    public void testInsert() throws Exception{
        when(employeeService.insert(any(Employee.class))).thenReturn(employee);

         // Convertendo objeto Employee para JSON
        String employeeJson = "{\"id\": 1, \"name\": \"Joao\", \"branch\": " +
                "\"Empresa1\", \"someField\": 3, \"anotherField\": 7, \"oneMoreField\": 7}";

        ResultActions result = mockMvc.perform(post("/employees")
                        .content(employeeJson)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id").value(employee.getId()))
                .andExpect((ResultMatcher) jsonPath("$.name").value(employee.getName()));
     }
     @Test
    public void testDelete() throws Exception{
        Long employeeId = 1L;
        doNothing().when(employeeService).delete(employeeId);

        ResultActions result = mockMvc.perform(delete("/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());

        verify(employeeService, times(1)).delete(employeeId);
     }
}
