package morrow.web.endpoint.matcher;

import morrow.web.endpoint.loader.LoaderException;

public class MatcherException extends LoaderException {

    public MatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatcherException(String message) {
        super(message);
    }
}
