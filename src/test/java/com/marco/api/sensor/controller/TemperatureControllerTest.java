package com.marco.api.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.dto.TemperatureRespDTO;
import com.marco.api.sensor.exception.NoValuesFoundException;
import com.marco.api.sensor.exception.ValueNotValidException;
import com.marco.api.sensor.service.TemperatureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TemperatureController.class)
public class TemperatureControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TemperatureService temperatureService;

    private TemperatureReqDTO reqDTO1;
    private TemperatureReqDTO reqDTO2;
    private TemperatureRespDTO respDTO;
    private List<TemperatureReqDTO> bulkReqDTO;

    @Before
    public void setUp() {
        reqDTO1 = new TemperatureReqDTO();
        reqDTO1.setValue(19.3);
        reqDTO2 = new TemperatureReqDTO();
        reqDTO2.setValue(21.3);

        respDTO = new TemperatureRespDTO();
        respDTO.setId(1);
        respDTO.setValue(19.3);

        bulkReqDTO = new ArrayList<>();
        bulkReqDTO.add(reqDTO1);
        bulkReqDTO.add(reqDTO2);
    }

    @Test
    public void givenAggregateTemperature_whenGetTemperature_thenReturnJson() throws Exception {

        AggregateRespDTO aggregateRespDTO = new AggregateRespDTO(AggregateRespDTO.AggregateMode.DAY,
                LocalDateTime.parse("2022-02-27T00:00:00"), 21.5);

        given(temperatureService.retrieveTemperatureData(AggregateRespDTO.AggregateMode.DAY))
                .willReturn(aggregateRespDTO);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/temperature")
                .param("mode", AggregateRespDTO.AggregateMode.DAY.name())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", is(21.5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aggregate_mode",
                        is(AggregateRespDTO.AggregateMode.DAY.toString())));
    }

    @Test
    public void givenAggregateTemperature_whenGetTemperature_andParamNotValid_thenReturnError() throws Exception {

        given(temperatureService.retrieveTemperatureData(null))
                .willThrow(new ValueNotValidException("mode"));

        mvc.perform(MockMvcRequestBuilders
                .get("/api/temperature")
                .param("mode", "MONTH")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("The value of mode is not valid"));
    }

    @Test
    public void givenAggregateTemperature_whenGetTemperature_andNoValueFound_thenReturnError() throws Exception {

        given(temperatureService.retrieveTemperatureData(AggregateRespDTO.AggregateMode.DAY))
                .willThrow(new NoValuesFoundException());

        mvc.perform(MockMvcRequestBuilders
                .get("/api/temperature")
                .param("mode", AggregateRespDTO.AggregateMode.DAY.name())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("No temperature values found"));
    }

    @Test
    public void whenValidInput_thenSaveTemperature() throws Exception {

        given(temperatureService.saveTemperature(reqDTO1)).willReturn(respDTO);

        mvc.perform(post("/api/temperature")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDTO1)))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.value", is(19.3)))
                .andReturn();
    }

    @Test
    public void whenValidInputBulk_thenSaveTemperatureBulk() throws Exception {

        given(temperatureService.saveTemperatureBulk(bulkReqDTO)).willReturn(new ArrayList<>());

        mvc.perform(post("/api/temperature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bulkReqDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
