package morrow.web.response.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import morrow.web.protocol.body.Body;

import java.io.ByteArrayInputStream;
import java.io.IOException;

class JsonBodySerializer implements BodySerializer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Body body(Object view) {
        try {
            var bytes = mapper.writeValueAsBytes(view);
            return Body.of(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
