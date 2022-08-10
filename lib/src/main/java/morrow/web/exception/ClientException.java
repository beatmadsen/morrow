package morrow.web.exception;

import morrow.Tracker;

public abstract class ClientException extends Exception implements WebException {

    @Override
    public void track() {
        Tracker.clientException(getMetaData(), this);
    }

    protected Tracker.MetaData getMetaData() {
        return new Tracker.MetaData();
    }
}
