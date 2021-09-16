package com.lufems.factorypal.domain.repository;

import com.lufems.factorypal.domain.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findByKey(String key);

    @Query("SELECT distinct M FROM Machine M JOIN Parameter P on M.id = P.id")
    List<Machine> findLatestParams();
}
