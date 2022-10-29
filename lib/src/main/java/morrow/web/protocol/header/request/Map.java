package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.HeaderMap;

import java.util.List;

public class Map extends HeaderMap {


    protected Map(java.util.Map<? extends morrow.web.protocol.header.FieldName<?>, List<String>> map) {
        super(map);
    }

    public <T extends FieldContent> List<T> get(FieldName<T> name) {
        return super.get(name);
    }
}
