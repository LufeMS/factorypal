package com.lufems.factorypal.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterMetrics {
    private String name;
    private Double average;
    private Double median;
    private Double minimum;
    private Double maximum;
}
