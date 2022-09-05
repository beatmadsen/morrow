package morrow.web.protocol.header.request;

import morrow.web.protocol.header.FieldContent;

class BaseFieldName<T extends FieldContent> extends morrow.web.protocol.header.BaseFieldName<T> implements FieldName<T> {

    BaseFieldName(String name, Class<T> type) {
        super(name, type);
    }
}
