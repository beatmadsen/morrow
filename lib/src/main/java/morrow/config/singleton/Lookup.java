package morrow.config.singleton;

import java.util.Optional;

public interface Lookup {
    default <T extends ManagedSingleton> T get(Class<T> type) {
        return find(type).orElseThrow(() -> new LookupException(type));
    }

    <T extends ManagedSingleton> Optional<T> find(Class<T> type);

}
