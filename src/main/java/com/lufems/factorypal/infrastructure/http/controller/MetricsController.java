package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.infrastructure.http.controller.model.MachineMetricRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/metrics")
public class MetricsController {

    @GetMapping("/{minutes}")
    public ResponseEntity<MachineMetricRest> getMetrics(@PathVariable("minutes") Integer minutes) {
        return ResponseEntity.ok(new MachineMetricRest());
    }
}
