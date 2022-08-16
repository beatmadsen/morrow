package morrow.web.protocol.mime;

import java.util.Map;
import java.util.Objects;

public interface MediaType {

    static MediaType freeHand(String type, String subtype, Map<String, String> parameters) {
        return new FreeHandMediaType(type, subtype, parameters);
    }
    static MediaType freeHand(String type, String subtype) {
        return new FreeHandMediaType(type, subtype, Map.of());
    }



    String contentTypeHeaderValue();

    Type type();

    Subtype subtype();

    default Key key() {
        var hash = Objects.hash(type(), subtype());
        return new Key(hash);
    }

    record Key(int key) {}

    enum Type {
        APPLICATION, TEXT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    enum Subtype {
        JSON, PLAIN, HTML;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

}
