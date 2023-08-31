package org.learning.RestPracticeSensor.util;

import org.learning.RestPracticeSensor.util.exceptions.MeasurementException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {

    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        errors.forEach(e -> errorMessage.append(e.getField())
                .append("-")
                .append(e.getDefaultMessage() == null ? e.getCode() : e.getDefaultMessage())
                .append(";"));

        throw new MeasurementException(errorMessage.toString());
    }
}
