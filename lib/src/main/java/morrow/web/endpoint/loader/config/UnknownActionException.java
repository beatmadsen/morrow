package morrow.web.endpoint.loader.config;

class UnknownActionException extends RuntimeException {
    UnknownActionException(String action) {
        super("Unknown action: '" + action + "'");
    }
}
