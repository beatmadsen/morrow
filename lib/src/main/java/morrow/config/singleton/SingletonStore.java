package morrow.config.singleton;

import java.util.concurrent.ConcurrentHashMap;

public class SingletonStore implements AutoCloseable {

    private final ConcurrentHashMap<Class<?>, ManagedSingleton> store = new ConcurrentHashMap<>();

    private static <T extends ManagedSingleton> T downcast(Class<T> type, ManagedSingleton s) {
        try {
            return type.cast(s);
        } catch (ClassCastException e) {
            throw new IncorrectTypeException(type, s, e);
        }
    }

    public <T extends ManagedSingleton> T get(Class<T> type) {
        var s = store.get(type);
        if (s == null) {
            throw new LookupException(type);
        }
        return downcast(type, s);
    }

    public void put(ManagedSingleton value) {
        store.put(value.getClass(), value);
    }

    @Override
    public void close() throws Exception {
        for (var s : store.values()) {
            s.close();
        }
    }

}
