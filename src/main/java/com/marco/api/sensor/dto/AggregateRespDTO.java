package com.marco.api.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The aggregated response DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class AggregateRespDTO {

    @JsonProperty("aggregate_mode")
    private AggregateMode aggregateMode;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("value")
    private Double value;


    /**
     * Enum to specify the  aggregation type
     */
    public static enum AggregateMode {
        HOUR, DAY
    }
}
