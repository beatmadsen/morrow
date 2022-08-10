package morrow.web.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonMediaTypeTest {

    @Test
    void contentTypeHeaderValue() {
        assertEquals("application/json;charset=UTF-8", CommonMediaType.JSON_UTF8.contentTypeHeaderValue());
    }
}