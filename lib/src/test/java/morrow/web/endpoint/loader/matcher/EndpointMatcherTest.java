package morrow.web.endpoint.loader.matcher;

import morrow.web.endpoint.Action;
import morrow.web.endpoint.matcher.EndpointMatcher;
import morrow.web.path.NamespaceSegment;
import morrow.web.path.PathSegment;
import morrow.web.path.ResourceSegment;
import morrow.web.path.UncategorisedSegment;
import morrow.web.request.Method;
import morrow.web.request.Path;
import morrow.web.request.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EndpointMatcherTest {

    @Test
    void emptyInputsShouldBePointless() {
        assertThrows(RuntimeException.class, () -> EndpointMatcher.from(List.of(), Set.of()));
    }

    @Test
    void shouldMatchCreateAction() {
        List<PathSegment> routePrefix = List.of(new ResourceSegment("cars"));
        var matcher = EndpointMatcher.from(routePrefix, Set.of(Action.CREATE, Action.FIND_MANY));
        var result = matcher.matches(List.of(new UncategorisedSegment("cars")), Method.POST);
        assertTrue(result);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldInferFindManyActionFromGetRequest() {
        var request = new Request(new Path(List.of(new UncategorisedSegment("cars"))), Method.GET, List.of());
        List<PathSegment> routePrefix = List.of(new ResourceSegment("cars"));
        var matcher = EndpointMatcher.from(routePrefix, Set.of(Action.CREATE, Action.FIND_MANY));
        var action = matcher.inferAction(request).orElseThrow();
        assertEquals(Action.FIND_MANY, action);
    }

    @Test
    void shouldInferUpdateByIdActionFromPatchRequest() {
        var request = new Request(new Path(List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("ships"), new UncategorisedSegment("46"))), Method.PATCH, List.of());
        List<PathSegment> routePrefix = List.of(new NamespaceSegment("namespace"), new ResourceSegment("ships"));
        var allActions = Arrays.stream(Action.values()).collect(Collectors.toSet());
        var matcher = EndpointMatcher.from(routePrefix, allActions);
        var action = matcher.inferAction(request).orElseThrow();
        assertEquals(Action.UPDATE_BY_ID, action);
    }

    @Test
    void shouldFailToInferUpdateByIdActionFromPatchRequestWithMissingParameter() {
        var request = new Request(new Path(List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("ships"))), Method.PATCH, List.of());
        List<PathSegment> routePrefix = List.of(new NamespaceSegment("namespace"), new ResourceSegment("ships"));
        var allActions = Arrays.stream(Action.values()).collect(Collectors.toSet());
        var matcher = EndpointMatcher.from(routePrefix, allActions);
        var action = matcher.inferAction(request);
        assertTrue(action.isEmpty());
    }
}