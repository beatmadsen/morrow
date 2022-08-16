package morrow.web.endpoint.loader.spec.tree;

import morrow.web.endpoint.loader.spec.EndpointSpec;
import morrow.web.path.PathSegment;

import java.util.List;

public record IndexedSpec(List<PathSegment> index, EndpointSpec config) {
}
