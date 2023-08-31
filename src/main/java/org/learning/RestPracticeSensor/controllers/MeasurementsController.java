package org.learning.RestPracticeSensor.controllers;

import jakarta.validation.Valid;
import org.learning.RestPracticeSensor.DTOs.MeasurementDTO;
import org.learning.RestPracticeSensor.DTOs.MeasurementResponse;
import org.learning.RestPracticeSensor.models.Measurement;
import org.learning.RestPracticeSensor.services.MeasurementsService;
import org.learning.RestPracticeSensor.util.errorResponse.MeasurementErrorResponse;
import org.learning.RestPracticeSensor.util.exceptions.MeasurementException;
import org.learning.RestPracticeSensor.util.validators.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.learning.RestPracticeSensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;

    private final ModelMapper mapper;

    private final MeasurementValidator validator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator validator, ModelMapper mapper) {
        this.measurementsService = measurementsService;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        validator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        measurementsService.add(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public MeasurementResponse getMeasurements() {
        return new MeasurementResponse(measurementsService.findAll()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long countRainyDays() {
        return measurementsService.findAll()
                .stream()
                .filter(Measurement::isRaining)
                .count();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return mapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return mapper.map(measurement, MeasurementDTO.class);
    }
}
