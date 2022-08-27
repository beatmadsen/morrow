package morrow.web.protocol.header.request;

import morrow.web.protocol.header.StringContent;

import java.util.Objects;

public class UnknownFieldName implements RequestHeaderFieldName {

    private final String name;

    public UnknownFieldName(String name) {
        this.name = name;
    }

    @Override
    public Key key() {
        return new Key(Objects.hash(this.getClass(), name), StringContent.class);
    }
}
