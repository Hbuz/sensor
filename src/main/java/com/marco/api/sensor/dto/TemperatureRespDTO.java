package com.marco.api.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The temperature response DTO used on save operation
 */
@Getter
@Setter
public class TemperatureRespDTO extends CiaoDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("value")
    private Double value;
}
