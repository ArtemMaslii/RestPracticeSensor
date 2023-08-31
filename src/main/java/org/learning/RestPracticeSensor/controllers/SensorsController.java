package org.learning.RestPracticeSensor.controllers;

import jakarta.validation.Valid;
import org.learning.RestPracticeSensor.DTOs.SensorDTO;
import org.learning.RestPracticeSensor.models.Sensor;
import org.learning.RestPracticeSensor.services.SensorsService;
import org.learning.RestPracticeSensor.util.errorResponse.MeasurementErrorResponse;
import org.learning.RestPracticeSensor.util.exceptions.MeasurementException;
import org.learning.RestPracticeSensor.util.validators.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.learning.RestPracticeSensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;

    private final SensorValidator sensorValidator;

    private final ModelMapper mapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper mapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.mapper = mapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {

        Sensor sensorToAdd = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        sensorsService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementException e) {
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return mapper.map(sensorDTO, Sensor.class);
    }
}
