package morrow.endpoint;

import morrow.rest.Method;

import java.util.List;

public enum Action {
    GET_BY_ID {
        @Override
        public List<Method> allowedMethods() {
            return List.of(Method.GET);
        }
    }, FIND_MANY {
        @Override
        public List<Method> allowedMethods() {
            return List.of(Method.GET);
        }
    }, CREATE {
        @Override
        public List<Method> allowedMethods() {
            return List.of(Method.POST);
        }
    }, UPDATE_BY_ID {
        @Override
        public List<Method> allowedMethods() {
            // https://github.com/rails/rails/pull/505
            return List.of(Method.PATCH, Method.PUT);
        }
    }, DELETE_BY_ID {
        @Override
        public List<Method> allowedMethods() {
            // Also POST with additional data
            return List.of(Method.DELETE, Method.POST);
        }
    };

    public abstract List<Method> allowedMethods();
}
