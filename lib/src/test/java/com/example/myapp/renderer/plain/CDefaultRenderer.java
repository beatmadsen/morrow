package com.example.myapp.renderer.plain;

import com.example.myapp.model.C;
import com.example.myapp.view.json.CLowVerbosityView;
import com.example.myapp.view.plain.CDefaultView;
import morrow.web.view.Renderer;
import morrow.web.view.routing.RendererRouter;

public class CDefaultRenderer extends Renderer<C, CDefaultView> {
    public CDefaultRenderer(RendererRouter router) {
        super(router);
    }

    @Override
    public CDefaultView render(C model) {
        return null;
    }
}
