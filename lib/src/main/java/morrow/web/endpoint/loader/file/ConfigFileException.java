package morrow.web.endpoint.loader.file;

class ConfigFileException extends RuntimeException {

    ConfigFileException(Exception e) {
        super("Could not parse endpoints.yml: " + e.getMessage(), e);
    }
}
