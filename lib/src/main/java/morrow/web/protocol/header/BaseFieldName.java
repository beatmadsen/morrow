package morrow.web.protocol.header;

public abstract class BaseFieldName<T extends FieldContent> implements FieldName<T> {
    private final String name;
    private final Class<T> type;

    public BaseFieldName(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Key<T> key() {
        return new Key<>(name.hashCode(), type);
    }
}
