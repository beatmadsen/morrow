package morrow.web.endpoint.loader.spec;

import morrow.web.endpoint.loader.spec.EndpointSpec;

class InvalidEndpointSpecException extends RuntimeException {
    InvalidEndpointSpecException(EndpointSpec endpointSpec, Exception e) {
        super("Validation of endpoint spec %s failed: %s".formatted(endpointSpec, e.getMessage()), e);
    }
}
