package morrow.web.protocol.header.request;

import morrow.web.protocol.header.StringContent;
import morrow.web.protocol.header.GeneralFieldName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

class MapTest {

    @Test
    void getAcceptContent() {
        var map = new Map(java.util.Map.of(FieldName.accept().key(), List.of("application/json")));

        List<AcceptContent> values = map.get(FieldName.accept());

        assertSame(AcceptContent.class, values.get(0).getClass());
    }

    @Test
    void getCacheControlContent() {
        var headerValue = "abc";
        var map = new Map(java.util.Map.of(GeneralFieldName.cacheControl().key(), List.of(headerValue)));

        List<StringContent> values = map.get(GeneralFieldName.cacheControl());

        assertSame(headerValue, values.get(0).value());
    }

}