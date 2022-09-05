package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.GeneralFieldName;

import java.util.List;
import java.util.stream.Stream;

public interface FieldName<T extends FieldContent> extends morrow.web.protocol.header.FieldName<T> {
    static List<FieldName<?>> known() {
        var knownRequestFieldNames = Stream.of(accept());
        return Stream.concat(GeneralFieldName.known().stream(), knownRequestFieldNames).toList();
    }

    static FieldName<AcceptContent> accept() {
        return new BaseFieldName<>("accept", AcceptContent.class);
    }

}
