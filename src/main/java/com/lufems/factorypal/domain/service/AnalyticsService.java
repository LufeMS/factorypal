package com.lufems.factorypal.domain.service;

import com.lufems.factorypal.domain.model.Machine;
import com.lufems.factorypal.domain.model.Parameter;
import com.lufems.factorypal.domain.model.ParameterMetrics;
import com.lufems.factorypal.domain.model.Summary;
import com.lufems.factorypal.domain.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    MachineRepository repository;

    @Autowired
    public AnalyticsService(MachineRepository repository) {
        this.repository = repository;
    }

    public List<Summary> getSummary(long minutes) {
        final LocalDateTime dateTime = LocalDateTime.now();
        final LocalDateTime subtractedDateTime = dateTime.minusMinutes(minutes);

        List<Machine> machines = this.repository.findAll();

        List<Summary> summaryList = machines.stream().map(
            machine -> {
                Summary summary = new Summary();
                summary.setMachine(machine.getName());

                Map<String, List<Parameter>> parametersGroupedByKey =
                        filterAndGroupParameters(machine.getParameters(), subtractedDateTime);

                parametersGroupedByKey.forEach((k, v) -> {
                    ParameterMetrics metrics = getParameterMetrics(k, v);
                    summary.getMetrics().add(metrics);
                });

                return summary;
        }).collect(Collectors.toList());

        return summaryList;
    }

    private ParameterMetrics getParameterMetrics(String k, List<Parameter> v) {
        ParameterMetrics metrics = new ParameterMetrics();
        metrics.setName(k);
        metrics.setMaximum(calculateMax(v));
        metrics.setMinimum(calculateMin(v));
        metrics.setAverage(calculateAvg(v));
        metrics.setMedian(calculateMed(v));
        return metrics;
    }

    private Double calculateMax(List<Parameter> parameters) {
        return parameters.stream()
                .max(Comparator.comparingDouble(Parameter::getValue))
                .map(p -> p.getValue())
                .orElse(0.0);
    }

    private Double calculateMin(List<Parameter> parameters) {
        return parameters.stream()
                .min(Comparator.comparingDouble(Parameter::getValue))
                .map(p -> p.getValue())
                .orElse(0.0);
    }

    private Double calculateAvg(List<Parameter> parameters) {
        if (parameters.size() == 0) return 0.0;

        return (parameters.stream()
                .map(Parameter::getValue)
                .reduce(0.0, Double::sum))
                / parameters.size();
    }

    private Double calculateMed(List<Parameter> parameters) {
        final int pSize = parameters.size();

        if (pSize == 0) {
            return 0.0;
        }

        if (pSize == 1) {
            return parameters.get(0).getValue();
        }

        final List<Parameter> sortedParams = parameters.stream()
                .sorted(Comparator.comparingDouble(Parameter::getValue))
                .collect(Collectors.toList());

        Double median;

        if (pSize % 2 == 0) {
            median = (sortedParams.get((pSize / 2) - 1).getValue() + sortedParams.get(pSize / 2).getValue()) / 2;
        } else {
            median = sortedParams.get(pSize / 2).getValue();
        }

        return median;
    }

    private Map<String, List<Parameter>> filterAndGroupParameters(Set<Parameter> parameters, LocalDateTime date) {
        return parameters.stream()
                .filter(parameter ->
                        parameter.getInsertionDate().isAfter(date) || parameter.getInsertionDate().isEqual(date))
                .collect(Collectors.groupingBy(Parameter::getKey));
    }
}
