package morrow.web.response.body;

import java.io.InputStream;

class StreamBody implements Body {

    private final InputStream stream;

    StreamBody(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public InputStream stream() {
        return stream;
    }
}
