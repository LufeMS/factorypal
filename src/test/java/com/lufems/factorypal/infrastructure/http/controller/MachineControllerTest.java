package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.service.MachineService;
import com.lufems.factorypal.domain.service.ParameterService;
import com.lufems.factorypal.infrastructure.mapper.MachineMapper;
import com.lufems.factorypal.infrastructure.mapper.ParameterMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MachineControllerTest {

    @MockBean
    private MachineService machineService;

    @MockBean
    private ParameterService parameterService;

    @Autowired
    private MockMvc mockMvc;

    private Machine mockMachine1 = new Machine();
    private Set<Parameter> mockParameters1 = new HashSet<>();

    private Machine mockMachine2 = new Machine();
    private Set<Parameter> mockParameters2 = new HashSet<>();

    private Machine mockMachine3 = new Machine();
    private Set<Parameter> mockParameters3 = new HashSet<>();

    @BeforeEach
    void setUp() {
        this.mockMachine1.setKey("ajoparametrit");
        this.mockMachine1.setName("Ajoparametrit");
        this.mockMachine1.setId(1L);

        mockParameters1.add(new Parameter(1L, "TS_setpoint_tail_length", 15.0, LocalDateTime.now(), this.mockMachine1));
        mockParameters1.add(new Parameter(2L, "perforation_length", 16.5, LocalDateTime.now(), this.mockMachine1));
        mockParameters1.add(new Parameter(3L, "core_interference", 15.0, LocalDateTime.now(), this.mockMachine1));
        mockParameters1.add(new Parameter(4L, "number_of_sheets", 17.7, LocalDateTime.now(), this.mockMachine1));

        this.mockMachine1.setParameters(mockParameters1);

        this.mockMachine2.setKey("aufwickler");
        this.mockMachine2.setName("Aufwickler");
        this.mockMachine2.setId(2L);

        mockParameters2.add(new Parameter(5L, "log_diameter", 15.0, LocalDateTime.now(), this.mockMachine2));
        mockParameters2.add(new Parameter(6L, "speed", 35.6, LocalDateTime.now(), this.mockMachine2));

        this.mockMachine2.setParameters(mockParameters2);

        this.mockMachine3.setKey("wickelkopf");
        this.mockMachine3.setName("wickelkopf");
        this.mockMachine3.setId(3L);

        mockParameters3.add(new Parameter(9L, "core_interference\n", 25.7, LocalDateTime.now(), this.mockMachine3));
        mockParameters3.add(new Parameter(10L, "speed", 27.7, LocalDateTime.now(), this.mockMachine3));

        this.mockMachine3.setParameters(mockParameters3);
    }

    @AfterEach
    void tearDown() {
        this.mockMachine1 = null;
        this.mockParameters1 = null;

        this.mockMachine2 = null;
        this.mockParameters2 = null;

        this.mockMachine3 = null;
        this.mockParameters3 = null;
    }

    @Test
    void shouldListAllMachinesWithLatestParams() throws Exception {
        when(machineService.listAllMachines()).thenReturn(Arrays.asList(mockMachine2, mockMachine3));
        when(parameterService.findLatestParameters()).thenReturn(Arrays.asList(
                new Parameter(5L, "log_diameter", 15.0, LocalDateTime.now(), this.mockMachine2),
                new Parameter(6L, "speed", 35.6, LocalDateTime.now(), this.mockMachine2),
                new Parameter(9L, "core_interference\n", 25.7, LocalDateTime.now(), this.mockMachine3),
                new Parameter(10L, "speed", 27.7, LocalDateTime.now(), this.mockMachine3)
        ));

        mockMvc.perform(get("/machines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void shouldFindAMachine() throws Exception {
        when(machineService.findMachine("ajoparametrit")).thenReturn(mockMachine1);

        mockMvc.perform(get("/machines/ajoparametrit"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Ajoparametrit"));
    }

    @Test
    void shouldNotFindAMachine() throws Exception {
        when(machineService.findMachine("aufwickler")).thenReturn(null);

        mockMvc.perform(get("/machines/aufwickler"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddParameters() {
    }
}