package com.example.myapp.renderer.json;

import com.example.myapp.model.C;
import com.example.myapp.view.json.CMediumVerbosityView;
import morrow.web.view.Renderer;
import morrow.web.view.routing.RendererRouter;

public class CMediumVerbosityRenderer extends Renderer<C, CMediumVerbosityView> {
    public CMediumVerbosityRenderer(RendererRouter router) {
        super(router);
    }

    @Override
    public CMediumVerbosityView render(C model) {
        return null;
    }
}
