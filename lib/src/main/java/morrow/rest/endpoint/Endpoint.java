package morrow.rest.endpoint;

import morrow.rest.Controller;
import morrow.rest.request.Path;
import morrow.rest.request.Request;

public abstract class Endpoint {
    public static Endpoint lookup(Path path) {
        return null;
    }

    public abstract Controller controller(Request request);
}
