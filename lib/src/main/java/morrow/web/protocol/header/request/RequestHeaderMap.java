package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.FieldName;
import morrow.web.protocol.header.HeaderMap;

import java.util.List;
import java.util.Map;

public class RequestHeaderMap extends HeaderMap {

    public RequestHeaderMap(Map<FieldName.Key, List<String>> map) {
        super(map);
    }

    public <T extends FieldContent> List<T> get(RequestHeaderFieldName name) {
        return super.get(name);
    }
}
