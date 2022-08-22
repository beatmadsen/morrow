package morrow.web.protocol.header;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;

class HeaderMapTest {

    @Test
    void getAcceptContent() {
        var content = new AcceptContent();
        var map = new HeaderMap(Map.of(CommonFieldName.ACCEPT.key()
                , List.of(content)));

        List<AcceptContent> values = map.get(CommonFieldName.ACCEPT);

        assertSame(content, values.get(0));
    }

    @Test
    void getCacheControlContent() {
        var content = new StringContent("abc");
        var map = new HeaderMap(Map.of(CommonFieldName.CACHE_CONTROL.key()
                , List.of(content)));

        List<StringContent> values = map.get(CommonFieldName.CACHE_CONTROL);

        assertSame(content, values.get(0));
    }
}