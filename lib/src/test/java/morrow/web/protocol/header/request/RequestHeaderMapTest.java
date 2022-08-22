package morrow.web.protocol.header.request;

import morrow.web.protocol.header.CommonFieldName;
import morrow.web.protocol.header.StringContent;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static morrow.web.protocol.header.request.RequestHeaderCommonFieldName.ACCEPT;
import static org.junit.jupiter.api.Assertions.assertSame;

class RequestHeaderMapTest {

    @Test
    void getAcceptContent() {
        var map = new RequestHeaderMap(Map.of(ACCEPT.key(), List.of("application/json")));

        List<AcceptContent> values = map.get(ACCEPT);

        assertSame(AcceptContent.class, values.get(0).getClass());
    }

    @Test
    void getCacheControlContent() {
        var headerValue = "abc";
        var map = new RequestHeaderMap(Map.of(CommonFieldName.CACHE_CONTROL.key(), List.of(headerValue)));

        List<StringContent> values = map.get(CommonFieldName.CACHE_CONTROL);

        assertSame(headerValue, values.get(0).value());
    }

    @Test
    void buildMap() {
        // TODO: user needs to be able to register own FieldName -> FieldContent type mappings
        var y = Map.of("accept", List.of("application/json+xml", "text/*;q=0.5"), "xa-size", List.of("100"));
    }
}