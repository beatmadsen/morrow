package morrow.endpoint.loader.config;

import morrow.endpoint.loader.LoaderException;

class ConfigException extends LoaderException {

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }
}
