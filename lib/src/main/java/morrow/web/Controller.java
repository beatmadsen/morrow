package morrow.web;

import morrow.Tracker;
import morrow.config.singleton.Lookup;
import morrow.web.exception.ClientException;
import morrow.web.protocol.mime.CommonMediaType;
import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;
import morrow.web.response.serialization.action.ActionResult;
import morrow.web.response.serialization.action.DefaultResult;
import morrow.web.response.serialization.action.ModelResult;
import morrow.web.view.ControllerRenderPlugin;

import java.util.List;
import java.util.function.Function;

public abstract class Controller {

    protected final State state;
    private final ControllerRenderPlugin controllerRenderPlugin;

    public Controller(State state) {
        this.state = state;
        controllerRenderPlugin = state.singletonLookup.get(ControllerRenderPlugin.class);
    }

    /**
     * Step intended for early validation of inputs, filtering,
     * looking up identified resources and certain redirects.
     * <p>
     * Should be overridden for steps that ought to be taken before business logic is triggered
     * and which may prevent it from happening
     *
     * @return a populated early result if bypassing business logic is appropriate
     * @throws ClientException if input from client is invalid
     */
    protected ActionResult beforeAction() throws ClientException {
        return ActionResult.empty();
    }


    public Response run() throws ClientException {
        ActionResult result = actionResult();
        Tracker.actionComplete(new Tracker.MetaData());
        return result.serialize(mimeNegotiation(state.accepts()));
    }

    private ActionResult actionResult() throws ClientException {
        // TODO: default result should be smarter than just empty body
        return beforeAction().or(this::action).or(() -> new DefaultResult(state.action()));
    }

    private ActionResult action() {
        return switch (state.action()) {
            case GET_BY_ID -> getById();
            case FIND_MANY -> findMany();
            case CREATE -> create();
            case UPDATE_BY_ID -> updateById();
            case DELETE_BY_ID -> deleteById();
        };
    }


    private MediaType mimeNegotiation(List<MediaType> accepts) {
        // TODO, see https://github.com/rails/rails/issues/9940
        return CommonMediaType.JSON_UTF8;
    }


    // show - GET /path/id
    protected ActionResult getById() {
        return ActionResult.empty();
    }


    // list - GET /path?x=42&y=hat
    protected ActionResult findMany() {
        return ActionResult.empty();
    }

    // create - POST /path including body
    protected ActionResult create() {
        return ActionResult.empty();
    }

    // update - PUT or PATCH /path/id
    protected ActionResult updateById() {
        return ActionResult.empty();
    }

    // destroy - DELETE /path/id
    protected ActionResult deleteById() {
        return ActionResult.empty();
    }

    protected ActionResult modelResult(Object model, String useCase) {
        Function<MediaType, Object> renderFn = (mediaType -> controllerRenderPlugin.render(model, mediaType, useCase));
        return new ModelResult(renderFn);
    }

    public record State(Action action, Lookup singletonLookup, List<MediaType> accepts) {
    }
}
