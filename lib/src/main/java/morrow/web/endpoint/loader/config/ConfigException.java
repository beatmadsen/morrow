package morrow.web.endpoint.loader.config;

import morrow.web.endpoint.loader.LoaderException;

class ConfigException extends LoaderException {

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }
}
