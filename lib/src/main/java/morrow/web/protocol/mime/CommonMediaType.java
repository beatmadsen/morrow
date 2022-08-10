package morrow.web.protocol.mime;

import java.util.Map;

public enum CommonMediaType implements MediaType {
    JSON_UTF8(Type.APPLICATION, Subtype.JSON, Map.of("charset", "UTF-8")),
    PLAIN_TEXT_UTF8(Type.TEXT, Subtype.PLAIN, Map.of("charset", "UTF-8"));


    private final Type type;
    private final Subtype subtype;
    private final Map<String, String> parameters;

    CommonMediaType(Type type, Subtype subtype, Map<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }

    @Override
    public String contentTypeHeaderValue() {
        return MediaType.freeHand(type.toString(), subtype.toString(), parameters).contentTypeHeaderValue();
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public Subtype subtype() {
        return subtype;
    }

}
