package com.example.myapp.renderer.json;

import com.example.myapp.model.C;
import com.example.myapp.view.json.CLowVerbosityView;
import morrow.web.view.Renderer;
import morrow.web.view.routing.MediaTypeSpecificRendererResolver;

public class CLowVerbosityRenderer extends Renderer<C, CLowVerbosityView> {
    public CLowVerbosityRenderer(MediaTypeSpecificRendererResolver router) {
        super(router);
    }

    @Override
    public CLowVerbosityView render(C model) {
        return new CLowVerbosityView();
    }
}
