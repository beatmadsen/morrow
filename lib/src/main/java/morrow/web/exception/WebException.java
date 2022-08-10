package morrow.web.exception;

import morrow.web.Response;

public interface WebException {
    Response response();
    void track();
}
