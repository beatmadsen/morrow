package com.example.myapp.controller;

import morrow.web.Controller;
import morrow.web.Response;
import morrow.web.exception.ClientException;

public class PartsController extends Controller {

    protected PartsController(State state) {
        super(state);
    }

    @Override
    public void beforeAction() throws ClientException {

    }

    @Override
    protected Response getById() {
        return null;
    }

    @Override
    protected Response findMany() {
        return null;
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
