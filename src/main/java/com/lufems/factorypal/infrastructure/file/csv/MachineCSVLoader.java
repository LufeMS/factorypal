package com.lufems.factorypal.infrastructure.file.csv;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MachineCSVLoader {

    MachineService service;

    @Value("classpath:${machine.csv.path}")
    Resource resource;

    @Autowired
    MachineCSVLoader(MachineService service) {
        this.service = service;
    }

    public void loadData() throws IOException {
        InputStream file = this.resource.getInputStream();
        List<Machine> machineList = getMachinesFromFile(file);
        this.service.saveAll(machineList);
    }

    private List<Machine> getMachinesFromFile(InputStream is) {

        List<Machine> machinesList = new ArrayList<>();

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            reader.readLine();; // To skip the first line which contains the header

            String line;
            while ((line = reader.readLine()) != null) {
                machinesList.add(CSVToMachineMapper(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return machinesList;

    }

    private Machine CSVToMachineMapper(String csvEntry) {
        String[] machineData = csvEntry.split(",");
        return new Machine(machineData[0], machineData[1]);
    }

}
