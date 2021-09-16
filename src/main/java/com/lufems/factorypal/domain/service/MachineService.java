package com.lufems.factorypal.domain.service;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MachineService {

    private MachineRepository repository;

    @Autowired
    MachineService(MachineRepository repository) {
        this.repository = repository;
    }

    public List<Machine> listAllMachines() {
        return this.repository.findAll();
    }

    public Machine findMachine(String key) {
        return this.repository.findByKey(key);
    }

    public void save(Machine machine) {
        this.repository.save(machine);
    }

    public void saveAll(Iterable<Machine> machines) {
        this.repository.saveAll(machines);
    }

}
