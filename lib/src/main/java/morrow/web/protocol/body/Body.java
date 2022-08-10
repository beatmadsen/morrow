package morrow.web.protocol.body;

import java.io.InputStream;

public interface Body {

    static Body of(String value) {
        return new StringBody(value);
    }

    static Body of(InputStream stream) {
        return new StreamBody(stream);
    }

    InputStream stream();
}
