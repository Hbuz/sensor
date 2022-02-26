package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.TemperatureDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Override
    public String saveTemperature(TemperatureReqDTO reqDTO) {
        return "temperature saved";
    }

    @Override
    public List<TemperatureDTO> retrieveTemperatureData() {
        List<TemperatureDTO> temperatureData = new ArrayList<>();
        return temperatureData;
    }
}
