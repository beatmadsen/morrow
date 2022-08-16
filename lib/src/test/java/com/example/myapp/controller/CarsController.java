package com.example.myapp.controller;

import morrow.web.Controller;
import morrow.web.protocol.body.Body;
import morrow.web.protocol.mime.CommonMediaType;
import morrow.web.response.Response;
import morrow.web.response.status.CommonStatusCode;

public class CarsController extends Controller {

    public CarsController(State state) {
        super(state);
    }

    @Override
    protected Response getById() {
        return null;
    }

    @Override
    protected Response findMany() {
        return new Response(
                CommonMediaType.JSON_UTF8,
                CommonStatusCode.OK,
                Body.of("{\"status\": \"ok\"}")
        );
    }

    @Override
    protected Response create() {
        return null;
    }

    @Override
    protected Response updateById() {
        return null;
    }

    @Override
    protected Response deleteById() {
        return null;
    }
}
