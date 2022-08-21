package morrow.web.response.serialization.action;

import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;

import java.util.function.Supplier;

public interface ActionResult {
    static ActionResult empty() {
        return new IncompleteResult();
    }

    static ActionResult of(Response response) {
        return (__) -> response;
    }

    default boolean isComplete() {
        return true;
    }

    Response serialize(MediaType mediaType);

    default ActionResult or(Supplier<ActionResult> s) {
        return this;
    }

}
