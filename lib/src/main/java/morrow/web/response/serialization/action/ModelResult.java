package morrow.web.response.serialization.action;

import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;
import morrow.web.view.ControllerRenderPlugin;

public class ModelResult implements ActionResult {
    private final Object model;
    private final ControllerRenderPlugin renderPlugin;

    public ModelResult(Object model, ControllerRenderPlugin renderPlugin) {
        this.model = model;
        this.renderPlugin = renderPlugin;
    }

    @Override
    public Response serialize(MediaType mediaType) {
        var view = renderPlugin.render(model, mediaType);
        return new ViewResult(view).serialize(mediaType);
    }
}
