package morrow.rest;

import morrow.Tracker;
import morrow.rest.exception.ClientException;

public abstract class Controller {

    public abstract void beforeAction() throws ClientException;

    public Response action() {
        var response = new Response();
        // TODO - route to correct action implementation
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
