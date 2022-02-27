package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.dto.TemperatureRespDTO;
import com.marco.api.sensor.model.Enums;

import java.util.List;

/**
 *
 */
public interface TemperatureService {

    /**
     * Service to store a single temperature value
     *
     * @param reqDTO The temperature value measured
     * @return The temperature value stored
     */
    TemperatureRespDTO saveTemperature(TemperatureReqDTO reqDTO);

    /**
     * Service to store multiple temperature values in bulk
     *
     * @param reqDTO A list of temperature values measured offline
     * @return A list of temperature values stored
     */
    List<TemperatureRespDTO> saveTemperatureBulk(List<TemperatureReqDTO> reqDTO);

    /**
     * Service to retrieve the aggregated temperature according to the mode
     *
     * @param aggregateMode Specifies the temperature aggregation mode
     * @return The aggregated temperature value with timestamp and mode
     */
    AggregateRespDTO retrieveTemperatureData(Enums.AggregateMode aggregateMode);
}
