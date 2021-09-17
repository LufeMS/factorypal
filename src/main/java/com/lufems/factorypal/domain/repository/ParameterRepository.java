package com.lufems.factorypal.domain.repository;

import com.lufems.factorypal.domain.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query("SELECT P from Parameter P WHERE P.insertionDate = (select MAX(aux.insertionDate) from Parameter aux where P.key = aux.key and P.machine.id = aux.machine.id)")
    List<Parameter> findLastestParameters();

    List<Parameter> findAllByInsertionDateGreaterThanEqual(LocalDateTime dateTime);
}
