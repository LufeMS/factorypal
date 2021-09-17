package com.lufems.factorypal.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "Parameters")
@EqualsAndHashCode(exclude = "machine")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;
    private Double value;
    private LocalDateTime insertionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    public Parameter(Long id, String key, Double value, LocalDateTime insertionDate, Machine machine) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.insertionDate = insertionDate;
        this.machine = machine;
    }

    public Parameter() {
    }
}
