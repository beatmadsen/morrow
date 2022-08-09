package com.example.myapp.controller;

import morrow.rest.Controller;
import morrow.rest.Response;
import morrow.rest.exception.ClientException;

public class OrdersController extends Controller {

    protected OrdersController(State state) {
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
