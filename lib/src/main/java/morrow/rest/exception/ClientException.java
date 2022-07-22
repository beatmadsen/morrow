package morrow.rest.exception;

import morrow.Tracker;

public abstract class ClientException extends Exception implements X {

    @Override
    public void track() {
        Tracker.clientException(getMetaData(), this);
    }

    protected Tracker.MetaData getMetaData() {
        return new Tracker.MetaData();
    }
}
