package morrow.web.endpoint.loader.config;

import jakarta.validation.ConstraintViolation;

import java.util.Set;
import java.util.stream.Collectors;

class ValidationException extends ConfigException {

    public ValidationException(Set<ConstraintViolation<EndpointConfig>> violations) {
        super(message(violations));
    }

    private static String message(Set<ConstraintViolation<EndpointConfig>> violations) {
        return "Invalid endpoint configuration: " +
                violations.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
