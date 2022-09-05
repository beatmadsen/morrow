package morrow.web.protocol.header;

import morrow.web.protocol.header.FieldName.Key;

import java.util.List;
import java.util.Map;

public abstract class HeaderMap {


    private final Map<? extends Key<?>, List<String>> map;

    protected HeaderMap(Map<? extends Key<?>, List<String>> map) {
        this.map = map;
    }

    protected <T extends FieldContent> List<T> get(FieldName<T> name) {
        var key = name.key();
        var list = map.get(key);
        return list.stream().map(v -> init(key.contentType(), v)).toList();
    }

    private static <T extends FieldContent> T init(Class<T> contentType, String value) {
        try {
            return contentType.getDeclaredConstructor(String.class).newInstance(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e); // TODO
        }
    }


}
