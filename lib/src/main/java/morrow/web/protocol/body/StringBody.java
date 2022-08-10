package morrow.web.protocol.body;

import java.io.ByteArrayInputStream;

class StringBody extends StreamBody {

    StringBody(String value) {
        super(new ByteArrayInputStream(value.getBytes()));
    }
}
