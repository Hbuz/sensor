package com.marco.api.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureReqDTO {

    @JsonProperty("value")
    private Double value;
}
