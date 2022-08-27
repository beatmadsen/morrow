package morrow.config.singleton;

import morrow.config.singleton.custom.X;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomSingletonLoaderTest {

    private Lookup singletonLookup;

    @BeforeEach
    void setUp() {
        singletonLookup = new Lookup() {
            @Override
            public <T extends ManagedSingleton> T get(Class<T> type) {
                return null;
            }
        };

    }

    @Test
    void loadingMissingPackage() {
        var packageName = "morrow.config.singleton.custom.does.not.exist";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        assertThrows(CustomSingletonLoader.MyEx.class, loader::loadSingletons);
    }

    @Test
    void loadingEmptyPackage() {
        var packageName = "morrow.config.singleton.custom.is.empty";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        assertThrows(CustomSingletonLoader.MyEx.class, loader::loadSingletons);
    }

    @Test
    void loadingPackageWithWrongClasses() {
        var packageName = "morrow.config.singleton.custom.is.wrong";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        assertThrows(CustomSingletonLoader.MyEx.class, loader::loadSingletons);
    }

    @Test
    void loadingPackageWithCorrectClasses() throws CustomSingletonLoader.MyEx {
        var packageName = "morrow.config.singleton.custom";
        var loader = new CustomSingletonLoader(singletonLookup, packageName);
        var list = loader.loadSingletons();
        assertSame(X.class, list.get(0).getClass());
    }
}