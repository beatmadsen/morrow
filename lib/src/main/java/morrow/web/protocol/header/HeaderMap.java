package morrow.web.protocol.header;

import java.util.List;
import java.util.Map;

public class HeaderMap {

    /*
    TODO, I want to be able to:
      1. Get the Accept header values and do mime negotiation on them
      1a. handle special values of the Accept header, like `* / *;q=0.8,text/*;q=0.9,application/json+xml`
      1b. allow for other types of special headers with encoded payload
      2. Get an integer value by looking up a header key
      3. I want to wrap the raw string information from the outside world in a typed boundary

     */

    private final Map<FieldName.Key, List<? extends FieldContent>> map;

    public HeaderMap(Map<FieldName.Key, List<? extends FieldContent>> map) {
        this.map = map;
    }

    public <T extends FieldContent> List<T> get(FieldName name) {
        var key = name.key();
        List<? extends FieldContent> values = map.get(key);
        return values.stream().map(v -> this.<T>getCast(key, v)).toList();
    }

    private <T extends FieldContent> T getCast(FieldName.Key key, FieldContent v) {

        return (T) key.contentType().cast(v);
    }
}
