package morrow.config;

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

    public static class IncorrectTypeException extends ConfigException {

        public IncorrectTypeException(Class<?> type, ManagedSingleton singleton, ClassCastException e) {
            super(message(type, singleton), e);
        }

        private static String message(Class<?> type, ManagedSingleton singleton) {
            return "Singleton lookup by type %s found instance of wrong type %s".formatted(
                    type.getCanonicalName(), singleton.getClass().getCanonicalName());
        }
    }

    public static class LookupException extends ConfigException {

        public LookupException(Class<?> key) {
            super("Did not find singleton of type %s".formatted(key));
        }
    }

}
