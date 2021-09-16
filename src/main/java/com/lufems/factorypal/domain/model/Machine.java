package com.lufems.factorypal.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Machines")
@Getter
@Setter
@EqualsAndHashCode(exclude = "parameters")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String key;
    private String name;

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Parameter.class)
    @Column(nullable = true)
    private Set<Parameter> parameters;

    public Machine(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Machine() {
    }
}
