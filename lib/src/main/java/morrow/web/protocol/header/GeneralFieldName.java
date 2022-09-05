package morrow.web.protocol.header;

import java.util.List;

public interface GeneralFieldName<T extends FieldContent> extends
        morrow.web.protocol.header.request.FieldName<T>,
        morrow.web.protocol.header.response.FieldName<T> {

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
