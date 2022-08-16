package morrow.web.endpoint.loader.spec;

import morrow.web.path.PathSegment;

import java.util.List;

public record IndexedSpec(List<PathSegment> index, EndpointSpec config) {
}
