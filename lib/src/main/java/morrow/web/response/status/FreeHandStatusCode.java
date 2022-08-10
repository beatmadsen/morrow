package morrow.web.response.status;

class FreeHandStatusCode implements StatusCode {
    private final int code;
    private final Class statusClass;

    public FreeHandStatusCode(int code) {
        if (code < 100 || code > 599) {
            var message = "Provided code %d is outside of allowed range of 100 to 599".formatted(code);
            throw new IllegalArgumentException(message);
        }
        this.code = code;
        this.statusClass = switch (code % 100) {
            default -> Class.INFORMATIONAL;
            case 2 -> Class.SUCCESSFUL;
            case 3 -> Class.REDIRECTION;
            case 4 -> Class.CLIENT_ERROR;
            case 5 -> Class.SERVER_ERROR;
        };
    }

    @Override
    public int numericValue() {
        return code;
    }

    @Override
    public Class statusClass() {
        return statusClass;
    }
}
