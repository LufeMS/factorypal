package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.service.MachineService;
import com.lufems.factorypal.domain.service.ParameterService;
import com.lufems.factorypal.infrastructure.http.controller.model.MachineRest;
import com.lufems.factorypal.infrastructure.http.controller.model.Request.NewMachineRequest;
import com.lufems.factorypal.infrastructure.http.controller.model.Request.NewParametersRequest;
import com.lufems.factorypal.infrastructure.mapper.MachineMapper;
import com.lufems.factorypal.infrastructure.mapper.ParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private MachineService machineService;
    private MachineMapper machineMapper;
    private ParameterMapper parameterMapper;
    private ParameterService parameterService;

    @Autowired
    MachineController(MachineService machineService, MachineMapper machineMapper, ParameterMapper parameterMapper, ParameterService parameterService) {
        this.machineService = machineService;
        this.machineMapper = machineMapper;
        this.parameterMapper = parameterMapper;
        this.parameterService = parameterService;
    }

    @GetMapping("")
    public ResponseEntity<List<MachineRest>> listAllMachines() {
        List<MachineRest> response = machineService.listAllMachines()
                 .stream()
                .map(this.machineMapper::domainToRest)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{machineKey}")
    public ResponseEntity<MachineRest> findMachine(@PathVariable(name = "machineKey") String key) {
        MachineRest response = machineMapper.domainToRest(machineService.findMachine(key));
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<MachineRest> addMachine(@RequestBody NewMachineRequest request) {
        Machine newMachine = machineMapper.newMachineRequestToDomain(request);
        machineService.save(newMachine);

        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        String path = "/machines/" + newMachine.getKey();

        return ResponseEntity.created(URI.create(baseUrl + path)).build();
    }

    @PostMapping("/parameters")
    public ResponseEntity<URI> addParameters(@RequestBody NewParametersRequest newParams) {

        List<Parameter> params = this.parameterMapper.requestToDomain(newParams);

        this.parameterService.saveAll(params);

        final String path = "/machines/" + newParams.getMachineKey();
        final String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        return ResponseEntity.created(URI.create(baseUrl + path)).build();
    }

}
