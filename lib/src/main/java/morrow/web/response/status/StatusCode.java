package morrow.web.response.status;

public interface StatusCode {
    static StatusCode freeHand(int code) {
        return new FreeHandStatusCode(code);
    }

    int numericValue();

    Class statusClass();

    enum Class {
        INFORMATIONAL, SUCCESSFUL, REDIRECTION, CLIENT_ERROR, SERVER_ERROR
    }

}
