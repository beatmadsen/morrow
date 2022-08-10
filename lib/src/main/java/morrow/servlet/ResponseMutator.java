package morrow.servlet;

import jakarta.servlet.http.HttpServletResponse;
import morrow.web.response.Response;

import java.io.IOException;
import java.io.InputStream;

public class ResponseMutator {
    private final HttpServletResponse out;

    public ResponseMutator(HttpServletResponse response) {
        this.out = response;
    }

    public void write(Response response) throws IOException {

        out.setContentType(response.mediaType().contentTypeHeaderValue());
        out.setStatus(response.statusCode().numericValue());

        writeBody(response.body().stream());

    }

    private void writeBody(InputStream body) throws IOException {
        try (
                var inputStream = body;
                var outputStream = out.getOutputStream()
        ) {
            var buffer = new byte[8192];
            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }
        }
    }

}
