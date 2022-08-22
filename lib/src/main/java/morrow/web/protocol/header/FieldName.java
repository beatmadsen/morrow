package morrow.web.protocol.header;

public interface FieldName {
    Key key();

    record Key(int key, Class<? extends FieldContent> contentType) {
    }

}
