package morrow.web.response.serialization.action;

import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;

import java.util.function.Supplier;

class IncompleteResult implements ActionResult {
    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public Response serialize(MediaType mediaType) {
        throw new IllegalStateException("result is empty");
    }

    @Override
    public ActionResult or(Supplier<ActionResult> s) {
        return s.get();
    }
}
