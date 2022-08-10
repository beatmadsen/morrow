package morrow.web.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardMediaTypeTest {

    @Test
    void contentTypeHeaderValue() {
        assertEquals("application/json;charset=UTF-8", StandardMediaType.JSON_UTF8.contentTypeHeaderValue());
    }
}