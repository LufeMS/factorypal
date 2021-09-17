package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.service.AnalyticsService;
import com.lufems.factorypal.domain.model.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class AnalyticsController {

    AnalyticsService service;

    @Autowired
    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("summary/{minutes}")
    public ResponseEntity<List<Summary>> getSummary(@PathVariable("minutes") Long minutes) {
        final List<Summary> summary = this.service.getSummary(minutes);
        return ResponseEntity.ok(summary);
    }
}
