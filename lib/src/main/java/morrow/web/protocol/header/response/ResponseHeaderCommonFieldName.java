package morrow.web.protocol.header.response;

import morrow.web.protocol.header.StringContent;

import java.util.Objects;

public enum ResponseHeaderCommonFieldName implements ResponseHeaderFieldName {
    CONTENT_TYPE {
        @Override
        public Key key() {
            return new Key(CONTENT_TYPE.keyHash(), StringContent.class);
        }
    },
    ;

    private int keyHash() {
        return Objects.hash(this.getClass(), this.ordinal());
    }
}
