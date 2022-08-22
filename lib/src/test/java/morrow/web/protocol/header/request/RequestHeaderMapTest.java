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
        var content = new AcceptContent();
        var map = new RequestHeaderMap(Map.of(ACCEPT.key(), List.of(content)));

        List<AcceptContent> values = map.get(ACCEPT);

        assertSame(content, values.get(0));
    }

    @Test
    void getCacheControlContent() {
        var content = new StringContent("abc");
        var map = new RequestHeaderMap(Map.of(CommonFieldName.CACHE_CONTROL.key(), List.of(content)));

        List<StringContent> values = map.get(CommonFieldName.CACHE_CONTROL);

        assertSame(content, values.get(0));
    }

    @Test
    void buildMap() {
        
    }
}