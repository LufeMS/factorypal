package com.lufems.factorypal.domain.service;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.model.Summary;
import com.lufems.factorypal.domain.repository.MachineRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AnalyticsServiceTest {

    @Autowired
    AnalyticsService service;

    @MockBean
    private MachineRepository repository;

    private List<Machine> machineList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Machine machine = new Machine();
        Set<Parameter> parameterList = new HashSet<>();

        machine.setKey("aufwickler");
        machine.setName("Aufwickler");
        machine.setId(2L);

        parameterList.add(new Parameter(5L, "log_diameter", 19.0, LocalDateTime.now(), machine));
        parameterList.add(new Parameter(5L, "log_diameter", 4.0, LocalDateTime.now(), machine));
        parameterList.add(new Parameter(5L, "log_diameter", 1.0, LocalDateTime.now().minusMinutes(7), machine));
        parameterList.add(new Parameter(5L, "log_diameter", 100.0, LocalDateTime.now().minusMinutes(11), machine));

        machine.setParameters(parameterList);
        machineList.add(machine);
    }

    @AfterEach
    void tearDown() {
        this.machineList.clear();
    }

    @Test
    void shouldCalculateSummaryCorrectly() {
        when(repository.findAll()).thenReturn(this.machineList);

        final List<Summary> summary = service.getSummary(10);

        Assertions.assertTrue(summary.get(0).getMetrics().get(0).getName().equals("log_diameter"));
        Assertions.assertTrue(summary.get(0).getMetrics().get(0).getMaximum() == 19.0);
        Assertions.assertTrue(summary.get(0).getMetrics().get(0).getMinimum() == 1.0);
        Assertions.assertTrue(summary.get(0).getMetrics().get(0).getMedian() == 4.0);
        Assertions.assertTrue(summary.get(0).getMetrics().get(0).getAverage() == 8.0);

    }
}