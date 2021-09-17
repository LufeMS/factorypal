package com.lufems.factorypal.infrastructure.mapper;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.repository.MachineRepository;
import com.lufems.factorypal.infrastructure.file.csv.model.ParameterCSV;
import com.lufems.factorypal.domain.model.Summary;
import com.lufems.factorypal.infrastructure.http.controller.model.Request.NewParametersRequest;
import com.lufems.factorypal.infrastructure.http.controller.model.ParameterRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParameterMapper {

    MachineRepository machineRepository;

    @Autowired
    public ParameterMapper(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public Parameter csvToDomain(ParameterCSV aParam) {
        Machine machine = machineRepository.findByKey(aParam.getMachineKey());
        if(machine == null) return null;

        Parameter mParam = new Parameter();

        mParam.setKey(aParam.getKey());
        mParam.setValue(aParam.getValue());

        mParam.setMachine(machine);
        mParam.setInsertionDate(LocalDateTime.now());

        return mParam;
    }

    public ParameterRest domainToRest(Parameter parameter) {
        ParameterRest rest = new ParameterRest();

        rest.setName(parameter.getKey());
        rest.setValue(parameter.getValue());

        return rest;
    }

    public List<Parameter> requestToDomain(NewParametersRequest newParams) {
        List<Parameter> params = new ArrayList<>();

        Machine machine = machineRepository.findByKey(newParams.getMachineKey());

        newParams.getParameters().forEach((k, v) -> {
            Parameter p = new Parameter();

            p.setKey(k);
            p.setValue(v);
            p.setMachine(machine);
            p.setInsertionDate(LocalDateTime.now());

            params.add(p);
        });

        return params;
    }
}
