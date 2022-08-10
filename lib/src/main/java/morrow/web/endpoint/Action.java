package morrow.web.endpoint;

import morrow.web.request.Method;

import java.util.List;

public enum Action {
    GET_BY_ID {
        @Override
        public List<Method> allowedMethods() {
            return List.of(Method.GET);
        }

        @Override
        public boolean hasShallowPath() {
            return true;
        }
    }, FIND_MANY {
        @Override
        public List<Method> allowedMethods() {
            return List.of(Method.GET);
        }

        @Override
        public boolean hasShallowPath() {
            return false;
        }
    }, CREATE {
        @Override
        public List<Method> allowedMethods() {
            return List.of(Method.POST);
        }

        @Override
        public boolean hasShallowPath() {
            return false;
        }
    }, UPDATE_BY_ID {
        @Override
        public List<Method> allowedMethods() {
            // https://github.com/rails/rails/pull/505
            return List.of(Method.PATCH, Method.PUT);
        }

        @Override
        public boolean hasShallowPath() {
            return true;
        }
    }, DELETE_BY_ID {
        @Override
        public List<Method> allowedMethods() {
            // Also POST with additional data
            return List.of(Method.DELETE, Method.POST);
        }

        @Override
        public boolean hasShallowPath() {
            return true;
        }
    };

    public abstract List<Method> allowedMethods();

    public abstract boolean hasShallowPath();
}
