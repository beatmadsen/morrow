package morrow.web.protocol.header;

public abstract class FieldName<T extends FieldContent> {
    private final String name;
    private final Class<T> type;

    public FieldName(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    public Key<T> key() {
        return new Key<>(name.hashCode(), type);
    }


    public record Key<U>(int key, Class<U> contentType) {
    }
}
