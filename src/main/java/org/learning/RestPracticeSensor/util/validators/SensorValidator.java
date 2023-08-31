package org.learning.RestPracticeSensor.util.validators;

import org.learning.RestPracticeSensor.models.Sensor;
import org.learning.RestPracticeSensor.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (this.sensorsService.getByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Such sensor already exists!");
        }
    }
}
