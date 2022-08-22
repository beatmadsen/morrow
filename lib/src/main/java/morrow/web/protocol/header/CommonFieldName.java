package morrow.web.protocol.header;

import morrow.web.protocol.header.request.RequestHeaderFieldName;
import morrow.web.protocol.header.response.ResponseHeaderFieldName;

public enum CommonFieldName implements RequestHeaderFieldName, ResponseHeaderFieldName {
    CACHE_CONTROL {
        @Override
        public Key key() {
            return new Key(this.ordinal(), StringContent.class);
        }
    },
}
