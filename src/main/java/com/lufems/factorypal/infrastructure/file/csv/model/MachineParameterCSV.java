package com.lufems.factorypal.infrastructure.file.csv.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineParameterCSV {
    private String key;
    private Double value;
    private String machineKey;
}
