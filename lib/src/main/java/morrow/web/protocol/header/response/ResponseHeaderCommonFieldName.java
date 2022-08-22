package morrow.web.protocol.header.response;

import morrow.web.protocol.header.StringContent;

public enum ResponseHeaderCommonFieldName implements ResponseHeaderFieldName {
    CONTENT_TYPE {
        @Override
        public Key key() {
            return new Key(this.ordinal(), StringContent.class);
        }
    },
}
