package morrow.web.protocol.header.response;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.StringContent;

public interface FieldName<T extends FieldContent> extends morrow.web.protocol.header.FieldName<T> {
    static FieldName<StringContent> contentType() {
        return new FieldName<StringContent>() {
            @Override
            public Key<StringContent> key() {
                return new Key<>(1, StringContent.class);
            }

            @Override
            public String toString() {
                return "content-type";
            }
        };
    }

}
