package morrow.config;

public abstract class ManagedSingleton implements AutoCloseable {
    protected final SingletonStore singletonStore;

    public ManagedSingleton(SingletonStore singletonStore) {

        this.singletonStore = singletonStore;
    }
}
