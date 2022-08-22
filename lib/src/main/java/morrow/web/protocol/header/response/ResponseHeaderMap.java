package morrow.web.protocol.header.response;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.FieldName;
import morrow.web.protocol.header.HeaderMap;

import java.util.List;
import java.util.Map;

public class ResponseHeaderMap extends HeaderMap {

    public ResponseHeaderMap(Map<FieldName.Key, List<String>> map) {
        super(map);
    }

    public <T extends FieldContent> List<T> get(ResponseHeaderFieldName name) {
        return super.get(name);
    }
}
