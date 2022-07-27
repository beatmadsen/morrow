package morrow.endpoint;

import morrow.rest.Method;

public enum Action {
    GET_BY_ID {
        @Override
        public Method method() {
            return Method.GET;
        }
    };

    public abstract Method method();
}
