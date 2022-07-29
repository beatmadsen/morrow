package morrow.endpoint.loader.config;

import morrow.endpoint.NamespaceSegment;
import morrow.endpoint.PathSegment;
import morrow.endpoint.ResourceSegment;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class Segmenter {
    public List<PathSegment> asSegments(String path) {
        var parts = parts(path);
        var reverseSegments = reverseAndMap(parts);
        return stream(reverseSegments.descendingIterator()).toList();
    }

    private static <T> Stream<T> stream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    private static Deque<PathSegment> reverseAndMap(Deque<String> parts) {
        return stream(new PathSegmentReverseIterator(parts)).collect(Collectors.toCollection(ArrayDeque::new));
    }

    private static Deque<String> parts(String path) {
        return Arrays.stream(path.split("/"))
                .filter(part -> !part.equals(""))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private static class PathSegmentReverseIterator implements Iterator<PathSegment> {
        private final Iterator<String> reverseParts;
        boolean isLastItem;

        public PathSegmentReverseIterator(Deque<String> parts) {
            this.reverseParts = parts.descendingIterator();
            isLastItem = true;
        }

        @Override
        public boolean hasNext() {
            return reverseParts.hasNext();
        }

        @Override
        public PathSegment next() {
            PathSegment segment;
            if (isLastItem) {
                segment = new ResourceSegment(reverseParts.next());
                isLastItem = false;
            } else {
                segment = new NamespaceSegment(reverseParts.next());
            }
            return segment;
        }
    }
}
