package morrow.web.protocol;

import java.util.Map;

public enum StandardMediaType implements MediaType {
    JSON_UTF8(Type.APPLICATION, Subtype.JSON, Map.of("charset", "UTF-8")),
    PLAIN_TEXT_UTF8(Type.TEXT, Subtype.PLAIN, Map.of("charset", "UTF-8"));


    private final Type type;
    private final Subtype subtype;
    private final Map<String, String> parameters;

    @Override
    public String contentTypeHeaderValue() {
        return MediaType.freeForm(type.toString(), subtype.toString(), parameters).contentTypeHeaderValue();
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

    StandardMediaType(Type type, Subtype subtype, Map<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }
}
