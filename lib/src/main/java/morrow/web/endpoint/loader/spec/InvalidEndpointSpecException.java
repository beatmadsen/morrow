package morrow.web.endpoint.loader.spec;

class InvalidEndpointSpecException extends RuntimeException {
    InvalidEndpointSpecException(EndpointSpec endpointSpec, Exception e) {
        super("Validation of endpoint spec %s failed: %s".formatted(endpointSpec, e.getMessage()), e);
    }
}
