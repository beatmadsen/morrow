package morrow.web.endpoint.loader;

import morrow.web.endpoint.EndpointException;

class LoaderException extends EndpointException {
    LoaderException(String s, Exception e) {
        super(s, e);
    }
}
