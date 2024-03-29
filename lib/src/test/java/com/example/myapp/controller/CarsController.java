package com.example.myapp.controller;

import com.example.myapp.model.B;
import com.example.myapp.model.C;
import morrow.web.Controller;
import morrow.web.response.serialization.action.ActionResult;
import morrow.web.response.serialization.action.ModelResult;
import morrow.web.response.serialization.action.ViewResult;

import java.util.Map;

public class CarsController extends Controller {

    public CarsController(State state) {
        super(state);
    }

    @Override
    protected ActionResult findMany() {
//        return new Response(
//                CommonMediaType.JSON_UTF8,
//                CommonStatusCode.OK,
//                Body.of("{\"status\": \"ok\"}")
//        );
        return new ViewResult(Map.of("status", "ok"));
    }

    @Override
    protected ActionResult getById() {
        return super.modelResult(new C(100), "verbosity-2");
    }
}
