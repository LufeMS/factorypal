package com.lufems.factorypal.infrastructure.http.controller.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MachineRest {
    private String name;
    private Set<ParameterRest> parameters;
}
