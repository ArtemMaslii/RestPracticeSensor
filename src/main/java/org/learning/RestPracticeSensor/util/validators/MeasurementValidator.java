package org.learning.RestPracticeSensor.util.validators;

import org.learning.RestPracticeSensor.models.Measurement;
import org.learning.RestPracticeSensor.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null) {
            return;
        }

        if (this.sensorsService.getByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with such hasn't been registered!");
        }
    }
}
