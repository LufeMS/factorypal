package com.lufems.factorypal.infrastructure.http.controller.model.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class NewParametersRequest {
    private String machineKey;
    private HashMap<String, Double> parameters;
}
