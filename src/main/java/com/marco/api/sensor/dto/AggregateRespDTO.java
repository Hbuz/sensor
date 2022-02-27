package com.marco.api.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marco.api.sensor.model.Enums;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The aggregated response DTO
 */
@Getter
@Setter
public class AggregateRespDTO {

    @JsonProperty("aggregate_mode")
    private Enums.AggregateMode aggregateMode;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("value")
    private Double value;
}
