package morrow.config.singleton;

public abstract class ManagedSingleton implements AutoCloseable {
    protected final Lookup singletonStore;

    public ManagedSingleton(Lookup singletonLookup) {

        this.singletonStore = singletonLookup;
    }

    @Override
    public void close() throws Exception {

    }
}
