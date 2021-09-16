package com.lufems.factorypal.infrastructure.mapper;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.infrastructure.http.controller.model.MachineRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MachineMapper {

    private ParameterMapper paramMapper;

    @Autowired
    public MachineMapper(ParameterMapper paramMapper) {
        this.paramMapper = paramMapper;
    }

    public MachineRest domainToRest(Machine machine) {
        MachineRest machineRest = new MachineRest();

        machineRest.setName(machine.getName());

        var parameters = machine.getParameters()
                .stream()
                .map(this.paramMapper::domainToRest)
                .collect(Collectors.toSet());

        machineRest.setParameters(parameters);

        return machineRest;
    }
}
