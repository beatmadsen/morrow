package morrow.config.singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomSingletonLoaderTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadingMissingPackage() {
        var packageName = "morrow.config.singleton.custom.does.not.exist";
        var loader = new CustomSingletonLoader(packageName);
        assertThrows(CustomSingletonLoader.MyEx.class, loader::loadSingletons);
    }

    @Test
    void loadingEmptyPackage() throws CustomSingletonLoader.MyEx {
        var packageName = "morrow.config.singleton.custom.is.empty";
        var loader = new CustomSingletonLoader(packageName);
        loader.loadSingletons();
//        assertThrows(CustomSingletonLoader.MyEx.class, loader::loadSingletons);
    }

    @Test
    void loadingPackageWithWrongClasses() throws CustomSingletonLoader.MyEx {
        var packageName = "morrow.config.singleton.custom.is.wrong";
        var loader = new CustomSingletonLoader(packageName);
        loader.loadSingletons();
//        assertThrows(CustomSingletonLoader.MyEx.class, loader::loadSingletons);
    }
}