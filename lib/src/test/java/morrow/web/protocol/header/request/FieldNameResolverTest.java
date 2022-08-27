package morrow.web.protocol.header.request;

import morrow.web.protocol.header.CommonFieldName;
import morrow.web.protocol.header.FieldContent;
import morrow.web.protocol.header.FieldName;
import morrow.web.protocol.header.StringContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class FieldNameResolverTest {

    private FieldNameResolver resolver;

    static class DemoContentType extends FieldContent {

        public DemoContentType(String value) {
            super(value);
        }
    }

    @BeforeEach
    void setUp() {
        resolver = FieldNameResolver.builder()
                .encode("x-fish", () -> new FieldName.Key(42, DemoContentType.class))
                .encode("y-mesh", () -> new FieldName.Key(43, StringContent.class))
                .build();
    }

    @Test
    void resolveKnown1() {
        var fieldName = resolver.resolve("x-fish");
        assertSame(DemoContentType.class, fieldName.key().contentType());
    }

    @Test
    void resolveKnown2() {
        var fieldName = resolver.resolve("y-fish");
        assertSame(StringContent.class, fieldName.key().contentType());
    }

    @Test
    void resolveUnknown() {
        var fieldName = resolver.resolve("not-registered-yet");
        assertSame(StringContent.class, fieldName.key().contentType());
    }

    @Test
    void resolveCacheControl() {
        var fieldName = resolver.resolve("cache-control");
        assertEquals(CommonFieldName.CACHE_CONTROL.key(), fieldName.key());
    }

    @Test
    void resolveAccept() {
        var fieldName = resolver.resolve("ACCEPT");
        assertEquals(RequestHeaderCommonFieldName.ACCEPT.key(), fieldName.key());
    }
}