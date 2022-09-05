package morrow.web.protocol.header.response;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.HeaderMap;

import java.util.List;

public class Map extends HeaderMap {

    public Map(java.util.Map<morrow.web.protocol.header.FieldName.Key<?>, List<String>> map) {
        super(map);
    }

    public <T extends FieldContent> List<T> get(FieldName<T> name) {
        return super.get(name);
    }
}
