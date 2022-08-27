package morrow.config.singleton;

import morrow.config.LoadHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomSingletonLoader {

    private final String packageName;
    private final LoadHelper loadHelper;

    private final Lookup singletonLookup;

    public CustomSingletonLoader(Lookup singletonLookup, String packageName) {
        this.singletonLookup = singletonLookup;
        this.packageName = packageName;
        loadHelper = new LoadHelper(ManagedSingleton.class);
    }

    public static class MyEx extends Exception {


        public MyEx(String message, Throwable cause) {
            super(message, cause);
        }

        public MyEx(String message) {
            super(message);
        }
    }

    public List<ManagedSingleton> loadSingletons() throws MyEx {
        var classes = findAllClassesUsingClassLoader(packageName);
        return instantiate(classes);

    }

    private List<ManagedSingleton> instantiate(Set<Class<ManagedSingleton>> classes) throws MyEx {
        try {
            return classes.stream().map(this::instantiate).toList();
        } catch (Exception e) {
            throw new MyEx("Failed to instantiate loaded singletons: " + e.getMessage(), e);
        }
    }

    private ManagedSingleton instantiate(Class<ManagedSingleton> c) {
        try {
            return c.getDeclaredConstructor(Lookup.class).newInstance(singletonLookup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Set<Class<ManagedSingleton>> findAllClassesUsingClassLoader(String packageName) throws MyEx {

        var systemResource = ClassLoader.getSystemResource(packageName.replaceAll("[.]", "/"));
        if (systemResource == null) {
            throw new MyEx("empty or not found");
        }
        try {
            InputStream stream = systemResource.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> className(line, packageName))
                    .map(this::loadClass)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new MyEx("mum", e);
        }
    }

    private Class<ManagedSingleton> loadClass(String className) {
        try {
            return loadHelper.loadClass(className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String className(String classFileName, String packageName) {
        return packageName + "."
                + classFileName.substring(0, classFileName.lastIndexOf('.'));
    }
}
