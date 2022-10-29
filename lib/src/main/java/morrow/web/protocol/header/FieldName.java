package morrow.web.protocol.header;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldName<?> fieldName)) return false;
        return name.equals(fieldName.name) && type.equals(fieldName.type);
    }

    public T initContent(String value) {
        try {
            return type.getDeclaredConstructor(String.class).newInstance(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e); // TODO
        }
    }
}
