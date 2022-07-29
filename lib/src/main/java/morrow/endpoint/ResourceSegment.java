package morrow.endpoint;

public class ResourceSegment extends PathSegment {

    public ResourceSegment(String value) {
        super(value);
    }

    @Override
    public boolean isResource() {
        return true;
    }
}
