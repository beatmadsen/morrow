package morrow.config.singleton;

public abstract class ManagedSingleton implements AutoCloseable {
    protected final SingletonStore singletonStore;

    public ManagedSingleton(SingletonStore singletonStore) {

        this.singletonStore = singletonStore;
    }

    @Override
    public void close() throws Exception {

    }
}
