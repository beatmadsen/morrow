package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;
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
                .encode("x-fish", new FieldName<>("x-fish", DemoContentType.class))
                .encode("y-mesh", new FieldName<>("y-mesh", StringContent.class))
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
        assertEquals(FieldName.cacheControl().key(), fieldName.key());
    }

    @Test
    void resolveAccept() {
        var fieldName = resolver.resolve("ACCEPT");
        assertEquals(FieldName.accept().key(), fieldName.key());
    }
}