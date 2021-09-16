package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.service.MachineService;
import com.lufems.factorypal.infrastructure.http.controller.model.MachineRest;
import com.lufems.factorypal.infrastructure.mapper.MachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private MachineService service;
    private MachineMapper mapper;

    @Autowired
    MachineController(MachineService service, MachineMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<List<MachineRest>> list() {
        List<MachineRest> response = service.listAllMachines()
                 .stream()
                .map(this.mapper::domainToRest)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{machineKey}")
    public ResponseEntity<MachineRest> findMachine(@PathVariable(name = "machineKey") String key) {
        MachineRest response = mapper.domainToRest(service.findMachine(key));
        return ResponseEntity.ok(response);
    }

}
