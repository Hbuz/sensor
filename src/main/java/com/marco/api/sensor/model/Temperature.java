package com.marco.api.sensor.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The temperature entity model representing the related DB table
 */
@Getter
@Setter
@Entity
@Table(name = "temperature")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "timestamp", nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime timestamp;

    @Column(name = "value", nullable = false, updatable = false)
    @NotNull
    private Double value;


    @Override
    public String toString() {
        return "{ id: '" + id + "\'," +
                " timestamp:'" + timestamp + "\'," +
                " value:'" + value + "\'" +
                "}";
    }
}