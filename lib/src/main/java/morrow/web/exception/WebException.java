package morrow.web.exception;

import morrow.web.response.Response;

public interface WebException {
    Response response();
    void track();
}
