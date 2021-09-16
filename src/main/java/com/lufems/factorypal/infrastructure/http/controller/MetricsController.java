package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.service.ParameterService;
import com.lufems.factorypal.infrastructure.http.controller.model.MachinesReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/metrics")
public class MetricsController {

    ParameterService service;

    @Autowired
    public MetricsController(ParameterService service) {
        this.service = service;
    }

    @GetMapping("/{minutes}")
    public ResponseEntity<MachinesReport> getMetrics(@PathVariable("minutes") String minutes) {
        this.service.findLatestParameters();
        return ResponseEntity.ok(new MachinesReport());
    }
}
