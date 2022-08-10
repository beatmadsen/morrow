package morrow.web.protocol;

import java.util.Map;

public interface MediaType {

    static MediaType freeForm(String type, String subtype, Map<String, String> parameters) {
        return () -> {
            StringBuilder sb = new StringBuilder();
            sb.append(type).append('/').append(subtype);
            for (Map.Entry<String, String> e : parameters.entrySet()) {
                sb.append(';').append(e.getKey().toLowerCase()).append('=').append(e.getValue());
            }
            return sb.toString();
        };
    }
    String contentTypeHeaderValue();
}
