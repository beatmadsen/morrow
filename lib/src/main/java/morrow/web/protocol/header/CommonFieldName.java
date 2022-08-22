package morrow.web.protocol.header;

public enum CommonFieldName implements FieldName {
    ACCEPT {
        @Override
        public Key key() {
            return new Key(this.ordinal(), AcceptContent.class);
        }
    },
    CACHE_CONTROL {
        @Override
        public Key key() {
            return new Key(this.ordinal(), StringContent.class);
        }
    },
    ;

}
