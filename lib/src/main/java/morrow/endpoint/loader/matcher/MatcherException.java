package morrow.endpoint.loader.matcher;

import morrow.endpoint.loader.LoaderException;

public class MatcherException extends LoaderException {

    public MatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatcherException(String message) {
        super(message);
    }
}
