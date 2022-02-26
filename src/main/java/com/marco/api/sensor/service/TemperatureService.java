package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.dto.TemperatureRespDTO;
import com.marco.api.sensor.exception.AggregateType;

import java.util.List;

public interface TemperatureService {

    TemperatureRespDTO saveTemperature(TemperatureReqDTO reqDTO);

    List<TemperatureRespDTO> saveTemperatureBulk(List<TemperatureReqDTO> reqDTO);

    AggregateRespDTO retrieveTemperatureData(AggregateType aggregateType);
}
