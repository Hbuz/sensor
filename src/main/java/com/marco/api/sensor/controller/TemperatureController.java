package com.marco.api.sensor.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.exception.EmptyValueBulkException;
import com.marco.api.sensor.exception.EmptyValueException;
import com.marco.api.sensor.exception.NoValuesFoundException;
import com.marco.api.sensor.exception.ValueNotValidException;
import com.marco.api.sensor.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Controller to handle temperature measurements
 */
@RestController
@RequestMapping("/api/temperature")
public class TemperatureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureController.class);

    private final TemperatureService temperatureService;

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    /**
     * Saves temperature measurement taking into account that the request can be a single measurement, or multiple
     * measurements sent in bulk
     *
     * @param reqDTO The request DTO that can be a single measurement or multiple in bulk
     * @return The measurement (or multiple) stored with id and timestamp
     */
    @PostMapping
    public ResponseEntity<Object> saveTemperature(@RequestBody Object reqDTO) {

        LOGGER.debug("saveTemperature - reqDTO:{}", reqDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {

            if (reqDTO instanceof Collection<?>) {
                List<TemperatureReqDTO> temperatureList = mapper.convertValue(reqDTO, new TypeReference<>() {
                });
                return new ResponseEntity<>(temperatureService.saveTemperatureBulk(temperatureList), HttpStatus.OK);

            } else {
                TemperatureReqDTO temperature = mapper.convertValue(reqDTO, TemperatureReqDTO.class);
                return new ResponseEntity<>(temperatureService.saveTemperature(temperature), HttpStatus.OK);
            }
        } catch (EmptyValueException  | EmptyValueBulkException e) {
            LOGGER.error("Failed saveTemperature request. Message:{}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Retrieves the aggregated data depending on the aggregation mode
     *
     * @param aggregateMode The aggregation mode.It can be hourly or daily
     * @return The aggregated data with mode and timestamp
     */
    @GetMapping
    public ResponseEntity<Object> retrieveTemperatureData(
            @RequestParam(value = "mode", required = false) AggregateRespDTO.AggregateMode aggregateMode) {

        LOGGER.debug("retrieveTemperatureData - aggregateMode:{}", aggregateMode);

        try {
            AggregateRespDTO response = temperatureService.retrieveTemperatureData(aggregateMode);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ValueNotValidException | NoValuesFoundException e) {

            LOGGER.error("Failed retrieveTemperatureData request. Message:{}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
