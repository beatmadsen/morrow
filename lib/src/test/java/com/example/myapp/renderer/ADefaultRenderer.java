package com.example.myapp.renderer;

import com.example.myapp.model.A;
import com.example.myapp.view.ADefaultView;
import morrow.web.view.Renderer;
import morrow.web.view.routing.MediaTypeSpecificRendererResolver;

public class ADefaultRenderer extends Renderer<A, ADefaultView> {
    public ADefaultRenderer(MediaTypeSpecificRendererResolver router) {
        super(router);
    }

    @Override
    public ADefaultView render(A model) {
        return null;
    }
}
