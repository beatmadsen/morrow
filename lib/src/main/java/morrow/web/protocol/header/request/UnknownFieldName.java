package morrow.web.protocol.header.request;

import morrow.web.protocol.header.StringContent;

class UnknownFieldName extends FieldName<StringContent> {

    UnknownFieldName(String name) {
        super(name, StringContent.class);
    }
}
