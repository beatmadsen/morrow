package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.StringContent;

import java.util.List;

public class FieldName<T extends FieldContent> extends morrow.web.protocol.header.FieldName<T> {
    public FieldName(String name, Class<T> type) {
        super(name, type);
    }

    public static List<FieldName<?>> known() {
        return List.of(accept(), cacheControl());
    }

    public static FieldName<AcceptContent> accept() {
        return new FieldName<>("accept", AcceptContent.class);
    }

    public static FieldName<StringContent> cacheControl() {
        return new FieldName<>("cache-control", StringContent.class);
    }
}
