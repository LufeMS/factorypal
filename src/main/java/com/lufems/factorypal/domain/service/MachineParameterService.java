package com.lufems.factorypal.domain.service;

import com.lufems.factorypal.domain.model.MachineParameter;
import com.lufems.factorypal.domain.repository.ParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineParameterService {

    ParametersRepository repository;

    @Autowired
    MachineParameterService(ParametersRepository repository) {
        this.repository = repository;
    }

    public void saveAll(Iterable<MachineParameter> parameters) {
        this.repository.saveAll(parameters);
    }
}
