package com.lufems.factorypal.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Summary {
    private String machine;
    private List<ParameterMetrics> metrics = new ArrayList<>();
}
