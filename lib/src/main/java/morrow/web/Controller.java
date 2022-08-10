package morrow.web;

import morrow.Tracker;
import morrow.config.SingletonStore;
import morrow.web.endpoint.Action;
import morrow.web.exception.ClientException;
import morrow.web.response.Response;

public abstract class Controller {

    protected Controller(State state) {
        this.state = state;
    }

    public record State(Action action, SingletonStore singletonStore) {

    }

    protected final State state;

    public abstract void beforeAction() throws ClientException;

    public Response action() {
        var response = switch (state.action()) {
            case GET_BY_ID -> getById();
            case FIND_MANY -> findMany();
            case CREATE -> create();
            case UPDATE_BY_ID -> updateById();
            case DELETE_BY_ID -> deleteById();
        };
        // TODO - route to correct action implementation
        Tracker.actionComplete(new Tracker.MetaData());
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
