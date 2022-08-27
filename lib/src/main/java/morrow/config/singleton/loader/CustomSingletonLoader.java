package morrow.config.singleton.loader;

import morrow.config.LoadHelper;
import morrow.config.singleton.Lookup;
import morrow.config.singleton.ManagedSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomSingletonLoader {

    private final String packageName;
    private final LoadHelper loadHelper;
    private final Lookup singletonLookup;

    CustomSingletonLoader(Lookup singletonLookup, String packageName) {
        this.singletonLookup = singletonLookup;
        this.packageName = packageName;
        loadHelper = new LoadHelper(ManagedSingleton.class);
    }

    public static List<ManagedSingleton> loadAll(Lookup singletonLookup) throws LoaderException {
        return new CustomSingletonLoader(singletonLookup, "morrow.config.singleton.custom").loadSingletons();
    }

    public List<ManagedSingleton> loadSingletons() throws LoaderException {
        var classes = loadClasses();
        return instantiate(classes);

    }

    private List<ManagedSingleton> instantiate(Set<Class<ManagedSingleton>> classes) throws LoaderException {
        try {
            return classes.stream().map(this::instantiate).toList();
        } catch (Exception e) {
            throw new LoaderException("Failed to instantiate loaded singletons: " + e.getMessage(), e);
        }
    }

    private ManagedSingleton instantiate(Class<ManagedSingleton> c) {
        try {
            return c.getDeclaredConstructor(Lookup.class).newInstance(singletonLookup);
        } catch (Exception e) {
            throw new InstantiationException(e);
        }
    }


    private Set<Class<ManagedSingleton>> loadClasses() throws LoaderException {
        var systemResource = ClassLoader.getSystemResource(packageName.replaceAll("[.]", "/"));
        if (systemResource == null) {
            throw new LoaderException("Package is empty or not found");
        }
        return loadClasses(systemResource);
    }

    private Set<Class<ManagedSingleton>> loadClasses(URL systemResource) throws LoaderException {
        try (BufferedReader reader = read(systemResource)) {
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(this::className)
                    .map(this::loadClass)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new LoaderException("Failed to load singletons: " + e.getMessage(), e);
        }
    }

    private static BufferedReader read(URL systemResource) throws LoaderException {
        try {
            return new BufferedReader(new InputStreamReader(systemResource.openStream()));
        } catch (IOException e) {
            throw new LoaderException("Could not read url " + systemResource, e);
        }
    }

    private Class<ManagedSingleton> loadClass(String className) {
        try {
            return loadHelper.loadClass(className);
        } catch (Exception e) {
            throw new ClassLoadException(e);
        }
    }

    private String className(String classFileName) {
        var endIndex = classFileName.lastIndexOf('.');
        return packageName + "." + classFileName.substring(0, endIndex);
    }

}
