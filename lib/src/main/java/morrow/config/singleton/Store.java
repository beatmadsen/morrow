package morrow.config.singleton;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Store implements AutoCloseable, Lookup {

    private final ConcurrentHashMap<Class<?>, ManagedSingleton> store = new ConcurrentHashMap<>();

    private static <T extends ManagedSingleton> T downcast(Class<T> type, ManagedSingleton s) {
        try {
            return type.cast(s);
        } catch (ClassCastException e) {
            throw new IncorrectTypeException(type, s, e);
        }
    }

    @Override
    public <T extends ManagedSingleton> Optional<T> find(Class<T> type) {
        return Optional.ofNullable(store.get(type)).map(s -> downcast(type, s));
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
