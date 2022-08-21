package morrow.web.response.serialization.action;

import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;

import java.util.function.Function;

public class ModelResult implements ActionResult {
    private final Function<MediaType, Object> renderFunction;

    public ModelResult(Function<MediaType, Object> renderFunction) {
        this.renderFunction = renderFunction;
    }


    @Override
    public Response serialize(MediaType mediaType) {
        var view = renderFunction.apply(mediaType);
        return new ViewResult(view).serialize(mediaType);
    }
}
