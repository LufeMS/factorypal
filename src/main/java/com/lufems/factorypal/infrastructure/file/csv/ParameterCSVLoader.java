package com.lufems.factorypal.infrastructure.file.csv;

import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.service.ParameterService;
import com.lufems.factorypal.infrastructure.file.csv.model.ParameterCSV;
import com.lufems.factorypal.infrastructure.mapper.ParameterMapper;
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
public class ParameterCSVLoader {

    ParameterService service;

    ParameterMapper mapper;

    @Value("classpath:${parameters.csv.path}")
    Resource resource;

    @Autowired
    ParameterCSVLoader(ParameterService service, ParameterMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public void loadData() throws IOException {
        InputStream file = this.resource.getInputStream();
        List<Parameter> parameterList = getParametersFromFile(file);

        if(!parameterList.isEmpty()) {
            this.service.saveAll(parameterList);
        }
    }

    private List<Parameter> getParametersFromFile(InputStream is) {

        List<Parameter> parameters = new ArrayList<>();

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            reader.readLine();; // To skip the first line which contains the header

            String line;
            while ((line = reader.readLine()) != null) {
                final Parameter parameter = mapper.csvToDomain(toParameterCSV(line));

                if(parameter == null) continue;

                parameters.add(parameter);
            }

        } catch (IOException e) { 
            e.printStackTrace();
        }

        return parameters;

    }

    private ParameterCSV toParameterCSV(String csvEntry) {
        String[] parameterData = csvEntry.split(",");
        ParameterCSV mParam = new ParameterCSV();
        mParam.setKey(parameterData[0]);
        mParam.setValue(Double.parseDouble(parameterData[1]));
        mParam.setMachineKey(parameterData[2]);
        return mParam;
    }
}
