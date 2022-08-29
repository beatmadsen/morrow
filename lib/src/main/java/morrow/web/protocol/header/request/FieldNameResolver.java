package morrow.web.protocol.header.request;

import morrow.web.protocol.header.CommonFieldName;

import java.util.HashMap;
import java.util.Map;

public class FieldNameResolver {

    public static Builder builder() {
        return new Builder();
    }

    private final Map<String, RequestHeaderFieldName> encodings;

    private FieldNameResolver(Map<String, RequestHeaderFieldName> encodings) {
        this.encodings = encodings;
    }

    public RequestHeaderFieldName resolve(String rawFieldName) {
        var result = encodings.get(rawFieldName.toLowerCase());
        return result == null ? new UnknownFieldName(rawFieldName) : result;
    }


    public static class Builder {

        private final Map<String, RequestHeaderFieldName> encodings;

        public Builder() {
            encodings = new HashMap<>();
            for (CommonFieldName fieldName : CommonFieldName.values()) {
                encodings.put(asHeaderName(fieldName.name()), fieldName);
            }
            for (RequestHeaderCommonFieldName fieldName : RequestHeaderCommonFieldName.values()) {
                encodings.put(asHeaderName(fieldName.name()), fieldName);
            }
        }

        private static String asHeaderName(String s) {
            return s.toLowerCase().replace("_", "-");
        }

        public Builder encode(String rawFieldName, RequestHeaderFieldName matchingField) {
            encodings.put(rawFieldName, matchingField);
            return this;
        }

        public Builder encode(Map<String, RequestHeaderFieldName> additionalEncodings) {
            encodings.putAll(additionalEncodings);
            return this;
        }

        public FieldNameResolver build() {
            return new FieldNameResolver(encodings);
        }

    }
}
