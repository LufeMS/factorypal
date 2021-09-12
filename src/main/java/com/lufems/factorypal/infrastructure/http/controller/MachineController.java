package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/machines")
public class MachineController {

    private MachineService service;

    @Autowired
    MachineController(MachineService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Machine>> list() {
        List<Machine> machineList = service.listAllMachines();
        return ResponseEntity.ok(machineList);
    }

    @GetMapping("/{machineKey}")
    public ResponseEntity<Machine> findMachine(@PathVariable(name = "machineKey") String key) {
        Machine machine = service.findMachine(key);
        return ResponseEntity.ok(machine);
    }

}
