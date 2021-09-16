package com.lufems.factorypal.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metrics {
    private Double average;
    private Double minimun;
    private Double maximum;
    private Double median;
    private Parameter param;
    private Machine machine;
}
