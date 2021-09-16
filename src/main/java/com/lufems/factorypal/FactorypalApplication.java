package com.lufems.factorypal;

import com.lufems.factorypal.infrastructure.file.csv.MachineCSVLoader;
import com.lufems.factorypal.infrastructure.file.csv.ParameterCSVLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FactorypalApplication implements CommandLineRunner {

	@Autowired
	MachineCSVLoader machineCSVLoader;

	@Autowired
    ParameterCSVLoader parameterCSVLoader;

	public static void main(String[] args) {
		SpringApplication.run(FactorypalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		machineCSVLoader.loadData();
		parameterCSVLoader.loadData();
	}
}
