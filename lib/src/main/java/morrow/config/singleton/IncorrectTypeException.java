package morrow.config.singleton;

class IncorrectTypeException extends RuntimeException {

    IncorrectTypeException(Class<?> type, ManagedSingleton singleton, ClassCastException e) {
        super(message(type, singleton), e);
    }

    private static String message(Class<?> type, ManagedSingleton singleton) {
        return "Singleton lookup by type %s found instance of wrong type %s".formatted(
                type.getCanonicalName(), singleton.getClass().getCanonicalName());
    }
}
