package com.lufems.factorypal.infrastructure.http.controller;

import com.lufems.factorypal.domain.service.ParameterService;
import com.lufems.factorypal.infrastructure.http.controller.model.NewParametersRequest;
import com.lufems.factorypal.infrastructure.mapper.ParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/parameters")
public class ParameterController {

    ParameterMapper mapper;
    ParameterService service;

    @Autowired
    public ParameterController(ParameterMapper mapper, ParameterService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<URI> addParameters(@RequestBody NewParametersRequest newParams) throws URISyntaxException {
        var params = this.mapper.requestToDomain(newParams);
        this.service.saveAll(params);

        final String path = "/machines/" + newParams.getMachineKey();

        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return ResponseEntity.created(URI.create(baseUrl + path)).build();
    }
}
