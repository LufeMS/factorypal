package com.lufems.factorypal.infrastructure.http.controller.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MachinesReport {
    private String machine;
    private Date timeSelected;
    private List<ParameterMetrics> metrics;
}
