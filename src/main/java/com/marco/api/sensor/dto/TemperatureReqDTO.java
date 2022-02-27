package com.marco.api.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The temperature request DTO containing the value measured
 */
@Getter
@Setter
public class TemperatureReqDTO {

    @JsonProperty("value")
    private Double value;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);


    @Override
    public String toString() {
        return "{ value: '" + value + "\'," +
                "  timestamp: '" + value + "\'" +
                "}";
    }
}
