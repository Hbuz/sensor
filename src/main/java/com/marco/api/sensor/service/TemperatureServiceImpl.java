package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.dto.TemperatureRespDTO;
import com.marco.api.sensor.exception.AggregateType;
import com.marco.api.sensor.model.Temperature;
import com.marco.api.sensor.repository.TemperatureRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public TemperatureRespDTO saveTemperature(TemperatureReqDTO temperatureReqDTO) {
        Temperature temperature = new Temperature();
        temperature.setValue(temperatureReqDTO.getValue());

        temperature = temperatureRepository.save(temperature);

        TemperatureRespDTO temperatureRespDTO = new TemperatureRespDTO();
        BeanUtils.copyProperties(temperatureRespDTO, temperature);
        return temperatureRespDTO;
    }

    @Override
    public List<TemperatureRespDTO> saveTemperatureBulk(List<TemperatureReqDTO> temperatureList) {
        List<TemperatureRespDTO> respList = new ArrayList<>();
        for (TemperatureReqDTO temperature : temperatureList) {
            this.saveTemperature(temperature);

            TemperatureRespDTO temperatureRespDTO = new TemperatureRespDTO();
            BeanUtils.copyProperties(temperatureRespDTO, temperature);
            respList.add(temperatureRespDTO);
        }
        return respList;
    }

    @Override
    public AggregateRespDTO retrieveTemperatureData(AggregateType aggregateType) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime current = null;
        switch (aggregateType) {
            case HOUR:
                current = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0);
                break;
            case DAY:
                current = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
                break;
        }

        Double value = temperatureRepository.getAggregatedTemperature(current);

        AggregateRespDTO aggregateRespDTO = new AggregateRespDTO();
        aggregateRespDTO.setAggregateType(aggregateType);
        aggregateRespDTO.setTimestamp(current);
        aggregateRespDTO.setValue(value);

        return aggregateRespDTO;
    }
}
