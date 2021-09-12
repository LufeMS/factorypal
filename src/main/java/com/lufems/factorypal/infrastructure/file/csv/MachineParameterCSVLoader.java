package com.lufems.factorypal.infrastructure.file.csv;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.MachineParameter;
import com.lufems.factorypal.domain.service.MachineParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MachineParameterCSVLoader {

    MachineParameterService service;

    @Value("classpath:${parameters.csv.path}")
    Resource resource;

    @Autowired
    MachineParameterCSVLoader(MachineParameterService service) {
        this.service = service;
    }

    public void loadData() throws IOException {
        InputStream file = this.resource.getInputStream();
        List<MachineParameter> parameterList = getParametersFromFile(file);
        this.service.saveAll(parameterList);
    }

    private List<MachineParameter> getParametersFromFile(InputStream is) {

        List<MachineParameter> parameters = new ArrayList<>();

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            reader.readLine();; // To skip the first line which contains the header

            String line;
            while ((line = reader.readLine()) != null) {
                parameters.add(CSVToParameterMapper(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parameters;

    }

    private MachineParameter CSVToParameterMapper(String csvEntry) {
        String[] parameterData = csvEntry.split(",");
        return new MachineParameter();
    }
}
