package morrow.rest;

import morrow.Tracker;
import morrow.rest.exception.ClientException;
import morrow.rest.request.Path;
import morrow.rest.request.Request;

import java.util.function.Function;

public abstract class Controller {
    public static Function<Request, Controller> forEndpoint(Path path) {
        return null;
    }

    public abstract void beforeAction() throws ClientException;

    public Response action() {
        var response = new Response();
        Tracker.success(new Tracker.MetaData());
        return response;
    }

    // path and query params etc available as controller state

    // show - GET /path/id
    protected abstract Response getById();

    // list - GET /path?x=42&y=hat
    protected abstract Response findMany();

    // create - POST /path including body
    protected abstract Response create();

    // update - PUT or PATCH /path/id
    protected abstract Response updateById();

    // destroy - DELETE /path/id
    protected abstract Response deleteById();
}
