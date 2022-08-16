package morrow.web.view.loader;

import morrow.web.view.ViewException;

class LoaderException extends ViewException {
    public LoaderException(Exception e) {
        super("Could not load render plugin: " + e.getMessage(), e);
    }
}
