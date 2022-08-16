package morrow.web.protocol;

import morrow.web.protocol.mime.CommonMediaType;
import morrow.web.protocol.mime.MediaType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonMediaTypeTest {

    @Test
    void contentTypeHeaderValue() {
        assertEquals("application/json;charset=UTF-8", CommonMediaType.JSON_UTF8.contentTypeHeaderValue());
    }

    @Test
    void key() {
        assertEquals(CommonMediaType.JSON_UTF8.key(), MediaType.freeHand("application", "json").key());
    }
}