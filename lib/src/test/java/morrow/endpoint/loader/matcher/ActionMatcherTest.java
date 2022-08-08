package morrow.endpoint.loader.matcher;

import morrow.endpoint.*;
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
    void createRequestWithCorrectPathMatches() {

        var requestPath = List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("parent"), new UncategorisedSegment("42"), new UncategorisedSegment("child"));
        var specification = List.of(new NamespaceSegment("namespace"), new ResourceSegment("parent"), new ParameterSegment(), new ResourceSegment("child"));
        var matcher = new ActionMatcher(specification, Action.CREATE);
        var result = matcher.matches(requestPath, Method.POST);
        assertTrue(result);
    }

    @Test
    void createRequestWithIncorrectPathDoesNotMatch() {

        var requestPath = List.of(new UncategorisedSegment("namespace"), new UncategorisedSegment("parent"), new UncategorisedSegment("child"));
        var specification = List.of(new NamespaceSegment("namespace"), new ResourceSegment("parent"), new ParameterSegment(), new ResourceSegment("child"));
        var matcher = new ActionMatcher(specification, Action.CREATE);
        var result = matcher.matches(requestPath, Method.POST);
        assertFalse(result);
    }
}