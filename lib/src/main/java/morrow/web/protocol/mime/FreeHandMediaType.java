package morrow.web.protocol.mime;

import java.util.Map;

class FreeHandMediaType implements MediaType {
    private final String type;
    private final String subtype;
    private final Map<String, String> parameters;


    FreeHandMediaType(String type, String subtype, Map<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }

    @Override
    public String contentTypeHeaderValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append('/').append(subtype);
        for (Map.Entry<String, String> e : parameters.entrySet()) {
            sb.append(';').append(e.getKey().toLowerCase()).append('=').append(e.getValue());
        }
        return sb.toString();
    }

    @Override
    public Type type() {
        return Type.valueOf(type.toUpperCase());
    }

    @Override
    public Subtype subtype() {
        return Subtype.valueOf(subtype.toUpperCase());
    }
}
