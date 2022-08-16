package com.example.myapp.renderer.json;

import com.example.myapp.model.A;
import com.example.myapp.view.json.ASpecialView1;
import morrow.web.view.Renderer;
import morrow.web.view.routing.RendererRouter;

public class ASpecialRenderer1 extends Renderer<A, ASpecialView1> {
    public ASpecialRenderer1(RendererRouter router) {
        super(router);
    }

    @Override
    public ASpecialView1 render(A model) {
        return null;
    }
}
