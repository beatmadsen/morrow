package morrow.endpoint.loader.config;

import morrow.path.PathSegment;

import java.util.List;

public record IndexedConfig(List<PathSegment> index, EndpointConfig config) {
}
