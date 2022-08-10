package morrow.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import morrow.MorrowApplication;
import morrow.web.endpoint.loader.InvalidConfigurationException;
import morrow.web.request.Method;
import morrow.web.request.Path;
import morrow.web.request.Request;

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

        var response = application.serve(map(req));
        new ResponseMutator(resp).write(response);

    }

    private static Request map(HttpServletRequest req) {
        return new Request(new Path(List.of()), Method.GET);
    }

    @Override
    public void destroy() {
        application.onShutdown();
    }
}
