package morrow.web.protocol.header.request;

import morrow.web.protocol.header.response.ResponseHeaderFieldName;

import java.util.Objects;

public enum RequestHeaderCommonFieldName implements RequestHeaderFieldName, ResponseHeaderFieldName {
    ACCEPT {
        @Override
        public Key key() {
            return new Key(ACCEPT.keyHash(), AcceptContent.class);
        }
    },

    ;

    private int keyHash() {
        return Objects.hash(this.getClass(), this.ordinal());
    }
}
