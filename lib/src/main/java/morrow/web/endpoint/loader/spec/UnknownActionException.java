package morrow.web.endpoint.loader.spec;

class UnknownActionException extends RuntimeException {
    UnknownActionException(String action) {
        super("Unknown action: '" + action + "'");
    }
}
