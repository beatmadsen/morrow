package morrow.web.endpoint.loader.spec;

import morrow.web.path.PathSegment;

import java.util.List;
import java.util.stream.Stream;

class ResourceTree {
    private final ResourceNode root;

    private ResourceTree(ResourceNode root) {
        this.root = root;
    }

    private static List<PathSegment> asSegments(String path) {
        return new Segmenter().asSegments(path);
    }

    private static Stream<IndexedSpec> traverseTree(ResourceNode node, List<PathSegment> routePrefix) {
        var newPrefix = Stream.concat(routePrefix.stream(), node.relativeRouteFromParent().stream()).toList();

        var ds = Stream.of(new IndexedSpec(newPrefix, node.config()));
        var cs = node.children().stream().flatMap(c -> traverseTree(c, newPrefix));

        return Stream.concat(ds, cs);
    }

    private static ResourceNode map(EndpointSpec config) {
        var subResources = config.subResources();
        Stream<EndpointSpec> configs = subResources == null ? Stream.of() : subResources.stream();
        var children = configs.map(ResourceTree::map).toList();
        return new ResourceNode(asSegments(config.path()), children, config);
    }

    /**
     * Build resource tree from input.
     * Assumes valid input.
     */
    static ResourceTree from(EndpointSpec config) {
        return new ResourceTree(map(config));
    }

    List<IndexedSpec> traverseTree() {
        return traverseTree(root, List.of()).toList();
    }

    private record ResourceNode(
            List<PathSegment> relativeRouteFromParent,
            List<ResourceNode> children,
            EndpointSpec config
    ) {

    }
}
