package morrow.config.singleton;

public interface Lookup {
    <T extends ManagedSingleton> T get(Class<T> type);
}
