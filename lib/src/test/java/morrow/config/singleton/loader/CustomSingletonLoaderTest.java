package morrow.config.singleton.loader;

import morrow.config.singleton.Lookup;
import morrow.config.singleton.ManagedSingleton;
import morrow.config.singleton.custom.X;
import morrow.config.singleton.custom.Y;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomSingletonLoaderTest {

    private Lookup singletonLookup;

    @BeforeEach
    void setUp() {
        singletonLookup = new Lookup() {

            @Override
            public <T extends ManagedSingleton> Optional<T> find(Class<T> type) {
                return Optional.empty();
            }
        };

    }

    @Test
    void loadingMissingPackage() {
        var packageName = "morrow.config.singleton.custom.does.not.exist";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        assertThrows(LoaderException.class, loader::loadSingletons);
    }

    @Test
    void loadingEmptyPackage() {
        var packageName = "morrow.config.singleton.custom.is.empty";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        assertThrows(LoaderException.class, loader::loadSingletons);
    }

    @Test
    void loadingPackageWithWrongClasses() {
        var packageName = "morrow.config.singleton.custom.is.wrong";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        assertThrows(LoaderException.class, loader::loadSingletons);
    }

    @Test
    void loadingPackageWithCorrectClasses() throws LoaderException {
        var packageName = "morrow.config.singleton.custom";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        var singletons = loader.loadSingletons();
        var classes = singletons.stream()
                .map(ManagedSingleton::getClass)
                .collect(Collectors.toSet());
        assertEquals(Set.of(X.class, Y.class), classes);
    }
}