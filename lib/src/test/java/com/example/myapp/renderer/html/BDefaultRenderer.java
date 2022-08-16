package com.example.myapp.renderer.html;

import com.example.myapp.model.B;
import com.example.myapp.view.html.BDefaultView;
import morrow.web.view.Renderer;
import morrow.web.view.routing.RendererRouter;

public class BDefaultRenderer extends Renderer<B, BDefaultView> {
    public BDefaultRenderer(RendererRouter router) {
        super(router);
    }

    @Override
    public BDefaultView render(B model) {
        return new BDefaultView();
    }
}
