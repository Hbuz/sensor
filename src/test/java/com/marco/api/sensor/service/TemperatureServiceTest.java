package com.marco.api.sensor.service;

import com.marco.api.sensor.dto.AggregateRespDTO;
import com.marco.api.sensor.dto.TemperatureReqDTO;
import com.marco.api.sensor.dto.TemperatureRespDTO;
import com.marco.api.sensor.exception.EmptyValueBulkException;
import com.marco.api.sensor.exception.EmptyValueException;
import com.marco.api.sensor.exception.NoValuesFoundException;
import com.marco.api.sensor.exception.ValueNotValidException;
import com.marco.api.sensor.model.Temperature;
import com.marco.api.sensor.repository.TemperatureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureServiceTest {

    @Mock
    TemperatureRepository temperatureRepository;

    @InjectMocks
    TemperatureServiceImpl temperatureService;

    private Temperature temperature1;
    private TemperatureReqDTO temperatureReqDTO1;
    private TemperatureReqDTO temperatureReqDTO2;
    private TemperatureReqDTO temperatureReqDTO3;
    private TemperatureReqDTO temperatureReqDTO4;
    private List<TemperatureReqDTO> temperatureReqDTOList1;
    private List<TemperatureReqDTO> temperatureReqDTOList2;

    private AggregateRespDTO aggregateRespDTO;

    @Before
    public void setUp() {

        temperature1 = new Temperature();
        temperature1.setValue(19.5);

        temperatureReqDTO1 = new TemperatureReqDTO();
        temperatureReqDTO1.setValue(19.5);

        temperatureReqDTO2 = new TemperatureReqDTO();

        temperatureReqDTO3 = new TemperatureReqDTO();
        temperatureReqDTO3.setValue(18.5);
        temperatureReqDTO3.setTimestamp(LocalDateTime.now());
        temperatureReqDTO4 = new TemperatureReqDTO();
        temperatureReqDTO4.setValue(22.5);
        temperatureReqDTO4.setTimestamp(LocalDateTime.now());
        temperatureReqDTOList1 = new ArrayList<>();
        temperatureReqDTOList1.add(temperatureReqDTO3);
        temperatureReqDTOList1.add(temperatureReqDTO4);

        temperatureReqDTOList2 = new ArrayList<>();
        temperatureReqDTOList2.add(temperatureReqDTO2);

        aggregateRespDTO = new AggregateRespDTO(AggregateRespDTO.AggregateMode.DAY,
                LocalDateTime.now().toLocalDate().atStartOfDay(), 18.4);
    }

    @Test
    public void whenSaveTemperature_thenReturnTemperatureValue() {

        when(temperatureRepository.save(any(Temperature.class))).thenReturn(temperature1);
        TemperatureRespDTO saved = temperatureService.saveTemperature(temperatureReqDTO1);

        assertEquals(temperatureReqDTO1.getValue(), saved.getValue().doubleValue(), 0.1);
    }

    @Test(expected = EmptyValueException.class)
    public void whenSaveTemperature_andInputIsNotValid_thenTriggerException() {

       temperatureService.saveTemperature(temperatureReqDTO2);
    }

    @Test
    public void whenSaveTemperatureInBulk_thenReturnTemperatureValueList() {

        when(temperatureRepository.save(any(Temperature.class))).thenReturn(temperature1);
        List<TemperatureRespDTO> saved = temperatureService.saveTemperatureBulk(temperatureReqDTOList1);

        assertEquals(2, saved.size());
    }

    @Test(expected = EmptyValueException.class)
    public void whenSaveTemperatureInBulk_andInputIsNotValid_thenTriggerException() {

        temperatureService.saveTemperatureBulk(temperatureReqDTOList2);
    }

    @Test(expected = EmptyValueBulkException.class)
    public void whenSaveTemperatureInBulk_andInputEmptyList_thenTriggerException() {

        temperatureService.saveTemperatureBulk(new ArrayList<>());
    }

    @Test(expected = EmptyValueBulkException.class)
    public void whenSaveTemperatureInBulk_andInputNull_thenTriggerException() {

        temperatureService.saveTemperatureBulk(null);
    }

    @Test
    public void whenRetrieveTemperature_andParamIsDay_thenReturnAggregateTemperatureValueDaily() {

        when(temperatureRepository.getAggregatedTemperature(any(LocalDateTime.class))).thenReturn(18.4);
        AggregateRespDTO data = temperatureService.retrieveTemperatureData(AggregateRespDTO.AggregateMode.DAY);

        assertEquals(18.4, data.getValue(), 0.1);
        assertEquals(AggregateRespDTO.AggregateMode.DAY, data.getAggregateMode());
        assertEquals(LocalDateTime.now().toLocalDate().atStartOfDay(), data.getTimestamp());
    }

    @Test
    public void whenRetrieveTemperature_andParamIsHour_thenReturnAggregateTemperatureValueDaily() {

        when(temperatureRepository.getAggregatedTemperature(any(LocalDateTime.class))).thenReturn(18.4);
        AggregateRespDTO data = temperatureService.retrieveTemperatureData(AggregateRespDTO.AggregateMode.HOUR);

        Integer currentHour = LocalDateTime.now().getHour();

        assertEquals(18.4, data.getValue(), 0.1);
        assertEquals(AggregateRespDTO.AggregateMode.HOUR, data.getAggregateMode());
        assertEquals(LocalDateTime.now().toLocalDate().atTime(currentHour, 0), data.getTimestamp());
    }

    @Test(expected = NoValuesFoundException.class)
    public void whenRetrieveTemperature_andParamIsHour_andNoValuesFound_thenTriggerException() {

        when(temperatureRepository.getAggregatedTemperature(any(LocalDateTime.class))).thenReturn(null);
        temperatureService.retrieveTemperatureData(AggregateRespDTO.AggregateMode.HOUR);
    }

    @Test(expected = ValueNotValidException.class)
    public void whenRetrieveTemperature_andParamIsNotValid_thenTriggerException() {

        temperatureService.retrieveTemperatureData(null);
    }
}
