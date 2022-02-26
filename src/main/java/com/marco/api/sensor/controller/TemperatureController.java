package com.marco.api.sensor.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.exception.AggregateType;
import com.marco.api.sensor.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @PostMapping
    public ResponseEntity<String> saveTemperature(@RequestBody Object reqDTO) {
        String response;

        ObjectMapper mapper = new ObjectMapper();

        if (reqDTO instanceof Collection<?>) {
            List<TemperatureReqDTO> temperatureList = mapper.convertValue(reqDTO, new TypeReference<>() {
            });
            response = temperatureService.saveTemperatureBulk(temperatureList).toString();

        } else {
            TemperatureReqDTO temperature = mapper.convertValue(reqDTO, TemperatureReqDTO.class);
            response = temperatureService.saveTemperature(temperature).toString();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AggregateRespDTO> retrieveTemperatureData(AggregateType aggregateType) {
        AggregateRespDTO response = temperatureService.retrieveTemperatureData(aggregateType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
