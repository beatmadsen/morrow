package morrow.config;

public class LoadHelper {

    private final Class<?> specification;

    public LoadHelper(Class<?> specification) {
        this.specification = specification;
    }

    public <T> Class<T> loadClass(String name) throws ClassLoadException {
        try {
            Class<?> aClass = Class.forName(name);
            if (!specification.isAssignableFrom(aClass)) {
                throw new ClassLoadException("Class with name %s did not extend or implement specified parent %s".formatted(name, specification.getCanonicalName()));
            }
            return (Class<T>) aClass;
        } catch (ClassNotFoundException e) {
            throw new ClassLoadException("Could not find class with name'" + name + "'", e);
        } catch (ClassCastException e) {
            throw new ClassLoadException("Failed to cast class with name %s to expected type".formatted(name), e);
        } catch (Exception e) {
            throw new ClassLoadException("Unexpected error while loading class with name %s: %s".formatted(name, e.getMessage()), e);
        }
    }

    public static class ClassLoadException extends Exception {
        public ClassLoadException(String message) {
            super(message);
        }

        public ClassLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
