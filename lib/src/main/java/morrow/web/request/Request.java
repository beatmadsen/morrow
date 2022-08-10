package morrow.web.request;

import morrow.web.protocol.mime.MediaType;

import java.util.List;

public record Request(Path path, Method method, List<MediaType> accepts) {

}
