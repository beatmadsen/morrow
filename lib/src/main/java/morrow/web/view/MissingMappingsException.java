package morrow.web.view;

import morrow.web.protocol.mime.MediaType;

class MissingMappingsException extends RuntimeException {

    public MissingMappingsException(MediaType mediaType) {
        super("Could not find view mappings for media type " + mediaType.contentTypeHeaderValue());
    }
}
