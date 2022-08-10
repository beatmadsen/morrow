package morrow.web.protocol.mime;

import java.util.Map;

public interface MediaType {

    static MediaType freeHand(String type, String subtype, Map<String, String> parameters) {
        return new FreeHandMediaType(type, subtype, parameters);
    }

    String contentTypeHeaderValue();

    Type type();

    Subtype subtype();

    enum Type {
        APPLICATION, TEXT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    enum Subtype {
        JSON, PLAIN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

}
