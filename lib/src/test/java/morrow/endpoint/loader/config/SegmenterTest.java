package morrow.endpoint.loader.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SegmenterTest {

    private Segmenter segmenter;

    @BeforeEach
    void setUp() {
        segmenter = new Segmenter();
    }

    @Test
    void shouldHandleLeadingAndTrailingAndMultipleSeparators() {
        var segments = segmenter.asSegments("///abc///def///ghi///");
        assertEquals(3, segments.size());
        assertTrue(segments.get(0).isNamespace());
        assertEquals("abc", segments.get(0).toString());
        assertTrue(segments.get(1).isNamespace());
        assertEquals("def", segments.get(1).toString());
        assertTrue(segments.get(2).isResource());
        assertEquals("ghi", segments.get(2).toString());
    }

    @Test
    void shouldHandleSingleNamespaceAndSingleSeparator() {
        var segments = segmenter.asSegments("space/cars");
        assertEquals(2, segments.size());
        assertTrue(segments.get(0).isNamespace());
        assertEquals("space", segments.get(0).toString());
        assertTrue(segments.get(1).isResource());
        assertEquals("cars", segments.get(1).toString());
    }

    @Test
    void shouldHandleSingleResource() {
        var segments = segmenter.asSegments("hats");
        assertEquals(1, segments.size());
        assertTrue(segments.get(0).isResource());
        assertEquals("hats", segments.get(0).toString());
    }
}