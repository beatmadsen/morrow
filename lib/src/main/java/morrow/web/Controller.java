package morrow.web;

import morrow.Tracker;
import morrow.config.singleton.SingletonStore;
import morrow.web.endpoint.Action;
import morrow.web.exception.ClientException;
import morrow.web.protocol.mime.CommonMediaType;
import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;
import morrow.web.response.rendering.BodyRenderer;
import morrow.web.response.status.CommonStatusCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Controller {

    protected final State state;
    protected final Map<String, Object> renderState;

    public Controller(State state) {
        this.state = state;
        renderState = new HashMap<>();
    }

    /**
     * Step intended for early validation of inputs, filtering,
     * looking up identified resources and certain redirects.
     * <p>
     * Should be overridden for steps that ought to be taken before business logic is triggered
     * and which may prevent it from happening
     *
     * @return a populated early response if bypassing business logic is appropriate
     * @throws ClientException if input from client is invalid
     */
    public Optional<Response> beforeAction() throws ClientException {
        return Optional.empty();
    }

    ;

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

    /*
     TODO:
      Should this be called implicitly by the end of an action?
      Action callbacks could return Optional<Response> as well
      falling back to a default rendering/empty body
    */
    protected Response render() {
        MediaType mediaType = mimeNegotiation(state.accepts());
        var body = BodyRenderer.forMediaType(mediaType).body(state.action(), renderState);
        // TODO: more dynamic. Some actions should return 201 or 301 by default
        return new Response(mediaType, CommonStatusCode.OK, body);
    }

    private MediaType mimeNegotiation(List<MediaType> accepts) {
        // TODO, see https://github.com/rails/rails/issues/9940
        return CommonMediaType.JSON_UTF8;
    }

    // show - GET /path/id
    protected abstract Response getById();

    // path and query params etc available as controller state

    // list - GET /path?x=42&y=hat
    protected abstract Response findMany();

    // create - POST /path including body
    protected abstract Response create();

    // update - PUT or PATCH /path/id
    protected abstract Response updateById();

    // destroy - DELETE /path/id
    protected abstract Response deleteById();

    public record State(Action action, SingletonStore singletonStore, List<MediaType> accepts) {
    }
}
