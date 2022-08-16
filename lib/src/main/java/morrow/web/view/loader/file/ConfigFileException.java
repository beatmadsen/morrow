package morrow.web.view.loader.file;

class ConfigFileException extends RuntimeException {
    ConfigFileException(Exception e) {
        super("Failed to load views.yml: " + e.getMessage(), e);
    }
}
