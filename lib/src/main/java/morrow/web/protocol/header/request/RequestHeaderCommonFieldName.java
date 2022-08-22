package morrow.web.protocol.header.request;

import morrow.web.protocol.header.response.ResponseHeaderFieldName;

public enum RequestHeaderCommonFieldName implements RequestHeaderFieldName, ResponseHeaderFieldName {
    ACCEPT {
        @Override
        public Key key() {
            return new Key(this.ordinal(), AcceptContent.class);
        }
    },
}
