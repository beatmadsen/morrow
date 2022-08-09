package morrow.rest;

import morrow.Tracker;
import morrow.config.SingletonStore;
import morrow.endpoint.Action;
import morrow.rest.exception.ClientException;

public abstract class Controller {

    protected Controller(State state) {
        this.state = state;
    }

    public record State(Action action, SingletonStore singletonStore) {

    }

    protected final State state;

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
