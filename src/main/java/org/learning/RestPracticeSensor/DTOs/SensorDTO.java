package org.learning.RestPracticeSensor.DTOs;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Name of sensor can't be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    public SensorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
