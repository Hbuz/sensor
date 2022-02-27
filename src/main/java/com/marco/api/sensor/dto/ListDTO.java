package com.marco.api.sensor.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The temperature response DTO used on save operation
 */
@Getter
@Setter
public class ListDTO extends CiaoDTO {

    List<TemperatureRespDTO> ciao;
}
