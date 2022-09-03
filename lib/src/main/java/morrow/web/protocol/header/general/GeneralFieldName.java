package morrow.web.protocol.header.general;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.StringContent;
import morrow.web.protocol.header.request.RequestHeaderFieldName;
import morrow.web.protocol.header.response.ResponseHeaderFieldName;

import java.util.List;

public interface GeneralFieldName<T extends FieldContent> extends RequestHeaderFieldName<T>, ResponseHeaderFieldName<T> {

    static GeneralFieldName<StringContent> cacheControl() {
        return new CacheControl();
    }

    static List<GeneralFieldName<?>> known() {
        return List.of(cacheControl());
    }

    class CacheControl implements GeneralFieldName<StringContent> {
        @Override
        public Key<StringContent> key() {
            return new Key<>(3, StringContent.class);
        }

        @Override
        public String toString() {
            return "cache-control";
        }
    }
}
