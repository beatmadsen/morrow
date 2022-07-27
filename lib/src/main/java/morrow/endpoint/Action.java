package morrow.endpoint;

import morrow.rest.Method;

public enum Action {
    GET_BY_ID {
        @Override
        public Method method() {
            return Method.GET;
        }
    }, FIND_MANY, CREATE, UPDATE_BY_ID, DELETE_BY_ID;

    public abstract Method method();
}
