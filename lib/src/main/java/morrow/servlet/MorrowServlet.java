package morrow.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import morrow.application.ApplicationException;
import morrow.application.MorrowApplication;
import morrow.web.protocol.mime.MediaType;
import morrow.web.request.RawRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@WebServlet(urlPatterns = "")
public class MorrowServlet extends HttpServlet {

    private final MorrowApplication application;

    public MorrowServlet() throws ApplicationException {
        application = new MorrowApplication();
    }

    private static <T> Stream<T> stream(Iterator<T> iter) {
        Iterable<T> i = () -> iter;
        return StreamSupport.stream(i.spliterator(), false);
    }

    private static RawRequest map(HttpServletRequest req) {

        var headers = stream(req.getHeaderNames().asIterator()).collect(
                Collectors.toMap(
                        Function.identity(),
                        h -> stream(req.getHeaders(h).asIterator()).toList()
                )
        );

        return new RawRequest(req.getMethod().toUpperCase(), req.getPathInfo(), headers);
    }

    private static List<MediaType> mapAcceptHeaders(Enumeration<String> headerValues) {
        Iterable<String> vs = headerValues::asIterator;
        // TODO: handle special values like "*/*;q=0.8, type/*, subtype/type1+type2"

        return StreamSupport.stream(vs.spliterator(), false).map(s -> {
            var split1 = s.split(";");
            var params = Arrays.stream(split1)
                    .skip(1)
                    .map(ps -> ps.split("="))
                    .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
            var mediaType = split1[0];
            var split2 = mediaType.split("/");
            return MediaType.freeHand(split2[0], split2[1], params);
        }).toList();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var response = application.serve(map(req));
        new ResponseMutator(resp).write(response);

    }

    @Override
    public void destroy() {
        application.onShutdown();
    }
}
