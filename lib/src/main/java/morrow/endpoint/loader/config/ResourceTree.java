package morrow.endpoint.loader.config;

import morrow.endpoint.PathSegment;

import java.util.List;
import java.util.stream.Stream;

public class ResourceTree {
    private final ResourceNode root;

    private ResourceTree(ResourceNode root) {
        this.root = root;
    }

    private static List<PathSegment> asSegments(String path) {
        return new Segmenter().asSegments(path);
    }


    public List<IndexedConfig> traverseTree() {
        return traverseTree(root, List.of()).toList();
    }

    private static Stream<IndexedConfig> traverseTree(ResourceNode node, List<PathSegment> routePrefix) {
        var newPrefix = Stream.concat(routePrefix.stream(), node.relativeRouteFromParent().stream()).toList();

        var ds = Stream.of(new IndexedConfig(newPrefix, node.config()));
        var cs = node.children().stream().flatMap(c -> traverseTree(c, newPrefix));

        return Stream.concat(ds, cs);
    }


    private static ResourceNode map(EndpointConfig config) {
        var subResources = config.getSubResources();
        Stream<EndpointConfig> configs = subResources == null ? Stream.of() : subResources.stream();
        var children = configs.map(ResourceTree::map).toList();
        return new ResourceNode(asSegments(config.getPath()), children, config);
    }

    public static ResourceTree from(EndpointConfig config) {
        return new ResourceTree(map(config));
    }

    private record ResourceNode(
            List<PathSegment> relativeRouteFromParent,
            List<ResourceNode> children,
            EndpointConfig config
    ) {

    }
}
