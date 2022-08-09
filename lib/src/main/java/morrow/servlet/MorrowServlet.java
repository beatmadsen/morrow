package morrow.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import morrow.MorrowApplication;
import morrow.endpoint.loader.InvalidConfigurationException;
import morrow.rest.Method;
import morrow.rest.request.Path;
import morrow.rest.request.Request;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "")
public class MorrowServlet extends HttpServlet {

    private final MorrowApplication application;

    public MorrowServlet() throws InvalidConfigurationException {
        application = new MorrowApplication();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var out = application.serve(map(req));

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print("{\"status\": \"ok\"}");

    }

    private static Request map(HttpServletRequest req) {
        return new Request(new Path(List.of()), Method.GET);
    }

    @Override
    public void destroy() {
        application.onShutdown();
    }
}
