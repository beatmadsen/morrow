package morrow.web.protocol.header;

public interface FieldName<T extends FieldContent> {

    Key<T> key();

    record Key<U>(int key, Class<U> contentType) {
    }
}
