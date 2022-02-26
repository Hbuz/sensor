package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.TemperatureDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;

import java.util.List;

public interface TemperatureService {

    String saveTemperature(TemperatureReqDTO reqDTO);

    List<TemperatureDTO> retrieveTemperatureData();
}
