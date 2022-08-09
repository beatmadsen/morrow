package morrow.path;

public class NamespaceSegment extends PathSegment {

    public NamespaceSegment(String value) {
        super(value);
    }

    @Override
    public boolean isNamespace() {
        return true;
    }

}
