package morrow.web.protocol.header.response;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.StringContent;

public class FieldName<T extends FieldContent> extends morrow.web.protocol.header.FieldName<T> {
    public FieldName(String name, Class<T> type) {
        super(name, type);
    }

    static FieldName<StringContent> contentType() {
        return new FieldName<>("content-type", StringContent.class);
    }

}
