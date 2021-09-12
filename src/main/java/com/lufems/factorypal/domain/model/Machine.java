package com.lufems.factorypal.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="Machines")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String key;
    private String name;

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<MachineParameter> parameters;

    public Machine(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Machine() {
    }
}
