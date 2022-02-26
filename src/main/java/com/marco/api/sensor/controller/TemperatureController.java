package com.marco.api.sensor.controller;

import com.marco.api.sensor.dto.TemperatureDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @PostMapping()
    public ResponseEntity<String> saveTemperature(@RequestBody TemperatureReqDTO reqDTO) {
        String response = temperatureService.saveTemperature(reqDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TemperatureDTO>> retrieveTemperatureData() {
        List<TemperatureDTO> response = temperatureService.retrieveTemperatureData();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
