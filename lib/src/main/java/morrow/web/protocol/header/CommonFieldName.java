package morrow.web.protocol.header;

import morrow.web.protocol.header.request.RequestHeaderFieldName;
import morrow.web.protocol.header.response.ResponseHeaderFieldName;

import java.util.Objects;

public enum CommonFieldName implements RequestHeaderFieldName, ResponseHeaderFieldName {
    CACHE_CONTROL {
        @Override
        public Key key() {
            return new Key(CACHE_CONTROL.keyHash(), StringContent.class);
        }


    },
    ;

    private int keyHash() {
        return Objects.hash(this.getClass(), this.ordinal());
    }
}
