package com.lufems.factorypal.domain.repository;

import com.lufems.factorypal.domain.model.MachineParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends JpaRepository<MachineParameter, Long> {
}
