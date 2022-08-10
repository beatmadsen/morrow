package morrow.servlet;

import jakarta.servlet.http.HttpServletResponse;
import morrow.web.response.Response;

import java.io.IOException;

public class ResponseMutator {
    private final HttpServletResponse out;

    public ResponseMutator(HttpServletResponse response) {
        this.out = response;
    }

    public void write(Response response) throws IOException {

        out.setContentType(response.mediaType().contentTypeHeaderValue());
        out.setStatus(HttpServletResponse.SC_OK);
        out.getWriter().print("{\"status\": \"ok\"}");
    }

}
