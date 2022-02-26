package com.marco.api.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marco.api.sensor.exception.AggregateType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AggregateRespDTO {

    @JsonProperty("aggregate_type")
    private AggregateType aggregateType;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("value")
    private Double value;
}
