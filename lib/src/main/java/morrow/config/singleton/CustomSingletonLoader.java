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

    public CustomSingletonLoader(String packageName) {
        this.packageName = packageName;
    }

    public static class MyEx extends Exception {


        public MyEx(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public List<ManagedSingleton> loadSingletons() throws MyEx {
        var classes = findAllClassesUsingClassLoader(packageName);

        return List.of();
    }


    private Set<Class<?>> findAllClassesUsingClassLoader(String packageName) throws MyEx {

        try {
            InputStream stream = ClassLoader.getSystemResource(packageName.replaceAll("[.]", "/")).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new MyEx("mum", e);
        }
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //TODO use LoadHelper
        }
    }
}
