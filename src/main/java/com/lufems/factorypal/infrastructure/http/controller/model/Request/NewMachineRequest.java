package com.lufems.factorypal.infrastructure.http.controller.model.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewMachineRequest {
    private String key;
    private String name;
}
