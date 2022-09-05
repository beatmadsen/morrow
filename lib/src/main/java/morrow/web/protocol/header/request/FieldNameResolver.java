package morrow.web.protocol.header.request;

import java.util.HashMap;
import java.util.Map;

public class FieldNameResolver {

    public static Builder builder() {
        return new Builder();
    }

    private final Map<String, FieldName<?>> encodings;

    private FieldNameResolver(Map<String, FieldName<?>> encodings) {
        this.encodings = encodings;
    }

    public FieldName<?> resolve(String rawFieldName) {
        var result = encodings.get(rawFieldName.toLowerCase());
        return result == null ? new UnknownFieldName(rawFieldName) : result;
    }


    public static class Builder {

        private final Map<String, FieldName<?>> encodings;

        public Builder() {
            encodings = new HashMap<>();
            for (FieldName<?> f : FieldName.known()) {
                encodings.put(f.toString(), f);
            }
        }

        public Builder encode(String rawFieldName, FieldName<?> matchingField) {
            encodings.put(rawFieldName, matchingField);
            return this;
        }

        public Builder encode(Map<String, FieldName<?>> additionalEncodings) {
            encodings.putAll(additionalEncodings);
            return this;
        }

        public FieldNameResolver build() {
            return new FieldNameResolver(encodings);
        }

    }
}
