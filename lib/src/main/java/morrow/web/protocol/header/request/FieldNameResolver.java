package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.general.GeneralFieldName;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FieldNameResolver {

    public static Builder builder() {
        return new Builder();
    }

    private final Map<String, RequestHeaderFieldName<?>> encodings;

    private FieldNameResolver(Map<String, RequestHeaderFieldName<?>> encodings) {
        this.encodings = encodings;
    }

    public RequestHeaderFieldName<?> resolve(String rawFieldName) {
        var result = encodings.get(rawFieldName.toLowerCase());
        return result == null ? new UnknownFieldName(rawFieldName) : result;
    }


    public static class Builder {

        private final Map<String, RequestHeaderFieldName<?>> encodings;

        public Builder() {
            encodings = new HashMap<>();
            knownFieldNames().forEach(f -> encodings.put(f.toString(), f));
        }

        private static Stream<RequestHeaderFieldName<?>> knownFieldNames() {
            return Stream.concat(GeneralFieldName.known().stream(), RequestHeaderFieldName.known().stream());
        }

        public Builder encode(String rawFieldName, RequestHeaderFieldName<?> matchingField) {
            encodings.put(rawFieldName, matchingField);
            return this;
        }

        public Builder encode(Map<String, RequestHeaderFieldName<?>> additionalEncodings) {
            encodings.putAll(additionalEncodings);
            return this;
        }

        public FieldNameResolver build() {
            return new FieldNameResolver(encodings);
        }

    }
}
