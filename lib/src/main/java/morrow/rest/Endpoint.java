package morrow.rest;

import morrow.rest.request.Path;
import morrow.rest.request.Request;

public interface Endpoint {
    static Endpoint lookup(Path path) {
        return null;
    }

    Controller controller(Request request);
}
