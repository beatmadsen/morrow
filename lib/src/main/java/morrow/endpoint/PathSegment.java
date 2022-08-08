package morrow.endpoint;

public abstract class PathSegment {

    protected final String value;

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

    public boolean matches(UncategorisedSegment reqSegment) {
        return value.equals(reqSegment.value);
    }
}
