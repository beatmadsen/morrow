package morrow.endpoint;

public class ParameterSegment extends PathSegment {

    public ParameterSegment() {
        super(null);
    }

    /**
     * Any uncategorised segment could potentially be a parameter
     */
    @Override
    public boolean matches(UncategorisedSegment reqSegment) {
        return true;
    }

    @Override
    public String toString() {
        return "<parameter>";
    }
}
