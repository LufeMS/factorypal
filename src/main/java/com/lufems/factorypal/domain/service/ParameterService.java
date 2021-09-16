package com.lufems.factorypal.domain.service;

import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {

    ParameterRepository repository;

    @Autowired
    ParameterService(ParameterRepository repository) {
        this.repository = repository;
    }

    public void saveAll(Iterable<Parameter> parameters) {
        this.repository.saveAll(parameters);
    }
}