package com.lufems.factorypal.infrastructure.mapper;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.infrastructure.http.controller.model.MachineRest;
import com.lufems.factorypal.infrastructure.http.controller.model.Request.NewMachineRequest;
import com.lufems.factorypal.infrastructure.http.controller.model.ParameterRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
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

        Set<ParameterRest> parameters = machine.getParameters()
                .stream()
                .map(this.paramMapper::domainToRest)
                .collect(Collectors.toSet());

        machineRest.setParameters(parameters);

        return machineRest;
    }

    public Machine newMachineRequestToDomain(NewMachineRequest pMachine) {
        Machine machine = new Machine();

        machine.setName(pMachine.getName());
        machine.setKey(pMachine.getKey());

        return machine;
    }
}
