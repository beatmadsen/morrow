package morrow.endpoint.loader.matcher;

import morrow.endpoint.*;
import morrow.endpoint.matcher.ActionMatcher;
import morrow.path.NamespaceSegment;
import morrow.path.ResourceSegment;
import morrow.path.UncategorisedSegment;
import morrow.rest.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionMatcherTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createRequestWithCorrectDeepPathMatches() {

        var requestPath = List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("parent"), new UncategorisedSegment("42"), new UncategorisedSegment("child"));
        var prefix = List.of(new NamespaceSegment("namespace"), new ResourceSegment("parent"), new ResourceSegment("child"));
        var matcher = ActionMatcher.from(prefix, Action.CREATE);
        var result = matcher.matches(requestPath, Method.POST);
        assertTrue(result);
    }

    @Test
    void createRequestWithCorrectShallowPathMatches() {

        var requestPath = List.of(
                new UncategorisedSegment("namespace"),
                new UncategorisedSegment("child_ns"),
                new UncategorisedSegment("child"),
                new UncategorisedSegment("42")
        );
        var prefix = List.of(
                new NamespaceSegment("namespace"),
                new ResourceSegment("parent"),
                new NamespaceSegment("child_ns"),
                new ResourceSegment("child")
        );
        var matcher = ActionMatcher.from(prefix, Action.GET_BY_ID);
        var result = matcher.matches(requestPath, Method.GET);
        assertTrue(result);
    }

    @Test
    void createRequestWithIncorrectShallowPathDoesNotMatch() {

        var requestPath = List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("parent"), new UncategorisedSegment("child_ns"), new UncategorisedSegment("child"));
        var prefix = List.of(new NamespaceSegment("namespace"), new ResourceSegment("parent"), new NamespaceSegment("child_ns"), new ResourceSegment("child"));
        var matcher = ActionMatcher.from(prefix, Action.GET_BY_ID);
        var result = matcher.matches(requestPath, Method.GET);
        assertFalse(result);
    }

    @Test
    void createRequestWithIncorrectDeepPathDoesNotMatch() {

        var requestPath = List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("parent"), new UncategorisedSegment("child"));
        var prefix = List.of(new NamespaceSegment("namespace"), new ResourceSegment("parent"), new ResourceSegment("child"));
        var matcher = ActionMatcher.from(prefix, Action.CREATE);
        var result = matcher.matches(requestPath, Method.POST);
        assertFalse(result);
    }
}