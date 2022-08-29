package morrow.web.request;

public record RawRequest(String method, String path, java.util.Map<String,java.util.List<String>> headers) {
}
