package morrow.web.response;

public enum CommonStatusCode implements StatusCode {
    /* 2xx */
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),

    /* 3xx */
    MOVED_PERMANENTLY(301),
    SEE_OTHER(303),
    NOT_MODIFIED(304),

    /* 4xx */
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),


    /* 5xx */
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    GATEWAY_TIMEOUT(504),
    ;

    private final StatusCode delegate;

    CommonStatusCode(int code) {
        this.delegate = new FreeHandStatusCode(code);
    }

    @Override
    public int numericValue() {
        return delegate.numericValue();
    }

    @Override
    public Class statusClass() {
        return delegate.statusClass();
    }
}
