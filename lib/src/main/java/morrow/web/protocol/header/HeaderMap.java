package morrow.web.protocol.header;

import java.util.List;
import java.util.Map;

public abstract class HeaderMap {


    private final Map<? extends FieldName<?>, List<String>> map;

    protected HeaderMap(Map<? extends FieldName<?>, List<String>> map) {
        this.map = map;
    }

    protected <T extends FieldContent> List<T> get(FieldName<T> name) {
        var list = map.get(name);
        return list.stream().map(name::initContent).toList();
    }


}
