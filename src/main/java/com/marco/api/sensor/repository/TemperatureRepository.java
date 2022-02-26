package com.marco.api.sensor.repository;

import com.marco.api.sensor.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {

    @Query(value = "SELECT AVG(t.value) FROM Temperature AS t WHERE t.timestamp >= :timestamp",
            nativeQuery = true)
    Double getAggregatedTemperature(LocalDateTime timestamp);
}
