package morrow.web.view;

import morrow.web.protocol.mime.CommonMediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerRenderPluginTest {

    private ControllerRenderPlugin renderPlugin;

    @BeforeEach
    void setUp() throws ControllerRenderPlugin.LoadException {
        renderPlugin = ControllerRenderPlugin.load();
    }

    @Test
    void render() {
        renderPlugin.render(new Object(), CommonMediaType.JSON_UTF8);
    }
}