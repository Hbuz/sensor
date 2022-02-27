package com.marco.api.sensor.repository;

import com.marco.api.sensor.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * The temperature repository used to interact with the related DB table
 */
@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {

    /**
     * Selects the average temperature measurement values stored from the timestamp specified
     *
     * @param timestamp the specified timestamp
     * @return the average temperature value
     */
    @Query(value = "SELECT AVG(t.value) FROM Temperature AS t WHERE t.timestamp >= :timestamp",
            nativeQuery = true)
    Double getAggregatedTemperature(LocalDateTime timestamp);
}
