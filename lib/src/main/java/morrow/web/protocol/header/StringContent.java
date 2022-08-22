package morrow.web.protocol.header;

public class StringContent extends FieldContent {

    public StringContent(String value) {
        super(value);
    }

    public String value() {
        return value;
    }
}
