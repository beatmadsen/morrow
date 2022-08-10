package morrow.web.protocol;

import java.util.Map;

public enum CommonMediaType implements MediaType {
    JSON_UTF8(Type.APPLICATION, Subtype.JSON, Map.of("charset", "UTF-8")),
    PLAIN_TEXT_UTF8(Type.TEXT, Subtype.PLAIN, Map.of("charset", "UTF-8"));

    private final MediaType delegate;

    CommonMediaType(Type type, Subtype subtype, Map<String, String> parameters) {
        this.delegate = MediaType.freeHand(type.toString(), subtype.toString(), parameters);
    }

    @Override
    public String contentTypeHeaderValue() {
        return delegate.contentTypeHeaderValue();
    }

    private enum Type {
        APPLICATION, TEXT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    private enum Subtype {
        JSON, PLAIN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }
}
