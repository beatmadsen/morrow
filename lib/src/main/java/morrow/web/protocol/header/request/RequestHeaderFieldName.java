package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.FieldName;

import java.util.List;

public interface RequestHeaderFieldName<T extends FieldContent> extends FieldName<T> {


    static List<RequestHeaderFieldName<?>> known() {
        return List.of(accept());
    }

    static RequestHeaderFieldName<AcceptContent> accept() {
        return new Accept();
    }

    class Accept implements RequestHeaderFieldName<AcceptContent> {
        @Override
        public Key<AcceptContent> key() {
            return new Key<>(42, AcceptContent.class);
        }

        @Override
        public String toString() {
            return "accept";
        }
    }
}
