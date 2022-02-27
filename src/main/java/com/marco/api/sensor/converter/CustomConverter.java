package com.marco.api.sensor.converter;

import com.marco.api.sensor.dto.AggregateRespDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Custom converter to handle not valid aggregate mode enum parameter
 */
@Component
class CustomConverter implements Converter<String, AggregateRespDTO.AggregateMode> {

    @Override
    public AggregateRespDTO.AggregateMode convert(String source) {
        try {
            return AggregateRespDTO.AggregateMode.valueOf(source);
        } catch(Exception e) {
            return null;
        }
    }
}