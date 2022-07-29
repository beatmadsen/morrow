package morrow.endpoint;

public abstract class PathSegment {

    private final String value;

    public PathSegment(String value) {
        this.value = value;
    }

    public boolean isNamespace() {
        return false;
    }

    public boolean isResource() {
        return false;
    }

    public String toString() {
        return value;
    }
}
