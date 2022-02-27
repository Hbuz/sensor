package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.dto.TemperatureRespDTO;
import com.marco.api.sensor.model.Enums;
import com.marco.api.sensor.model.Temperature;
import com.marco.api.sensor.repository.TemperatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class TemperatureServiceImpl implements TemperatureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TemperatureRespDTO saveTemperature(TemperatureReqDTO temperatureReqDTO) {

        LOGGER.debug("saveTemperature - temperatureReqDTO:{}", temperatureReqDTO);

        Temperature temperature = new Temperature();
        temperature.setValue(temperatureReqDTO.getValue());
        temperature.setTimestamp(temperatureReqDTO.getTimestamp());

        temperature = temperatureRepository.save(temperature);

        TemperatureRespDTO temperatureRespDTO = new TemperatureRespDTO();
        BeanUtils.copyProperties(temperature, temperatureRespDTO);
        return temperatureRespDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TemperatureRespDTO> saveTemperatureBulk(List<TemperatureReqDTO> temperatureList) {

        LOGGER.debug("saveTemperatureBulk - temperatureList:{}", temperatureList);

        TemperatureRespDTO temperatureRespDTO;
        List<TemperatureRespDTO> respList = new ArrayList<>();
        for (TemperatureReqDTO temperature : temperatureList) {
            temperatureRespDTO = this.saveTemperature(temperature);
//            TemperatureRespDTO temperatureRespDTO = new TemperatureRespDTO();
//            BeanUtils.copyProperties(temperature, temperatureRespDTO);
            respList.add(temperatureRespDTO);
        }
        return respList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AggregateRespDTO retrieveTemperatureData(Enums.AggregateMode aggregateMode) {

        LOGGER.debug("retrieveTemperatureData - aggregateType:{}", aggregateMode);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime current = null;
        switch (aggregateMode) {
            case HOUR:
                current = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0);
                break;
            case DAY:
                current = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
                break;
        }

        Double value = temperatureRepository.getAggregatedTemperature(current);

        AggregateRespDTO aggregateRespDTO = new AggregateRespDTO();
        aggregateRespDTO.setAggregateMode(aggregateMode);
        aggregateRespDTO.setTimestamp(current);
        aggregateRespDTO.setValue(value);

        return aggregateRespDTO;
    }
}
