package morrow.web.endpoint.loader.config;

import morrow.web.path.PathSegment;

import java.util.List;

public record IndexedSpec(List<PathSegment> index, EndpointSpec config) {
}
