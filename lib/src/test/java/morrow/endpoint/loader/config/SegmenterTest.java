package morrow.endpoint.loader.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmenterTest {

    private Segmenter segmenter;

    @BeforeEach
    void setUp() {
        segmenter = new Segmenter();
    }

    @Test
    void shouldSplitIntoCorrectSegments() {
        var segments = segmenter.asSegments("///abc///def///ghi///");
        assertEquals(3, segments.size());
        assertTrue(segments.get(0).isNamespace());
        assertEquals("abc", segments.get(0).toString());
        assertTrue(segments.get(1).isNamespace());
        assertEquals("def", segments.get(1).toString());
        assertTrue(segments.get(2).isResource());
        assertEquals("ghi", segments.get(2).toString());
    }
}