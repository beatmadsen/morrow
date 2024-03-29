package morrow.web.view;

import com.example.myapp.model.B;
import com.example.myapp.model.C;
import morrow.config.singleton.Store;
import morrow.config.validation.Validation;
import morrow.web.protocol.mime.CommonMediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerRenderPluginTest {

    private ControllerRenderPlugin renderPlugin;

    @BeforeEach
    void setUp() throws ViewException {
        var singletonStore = new Store();
        singletonStore.put(new Validation(null));
        renderPlugin = ControllerRenderPlugin.load(singletonStore);
    }

    @Test
    void renderDefaultJsonViewOfB() {
        var result = renderPlugin.render(new B(), CommonMediaType.JSON_UTF8);
        assertTrue(result instanceof com.example.myapp.view.json.BDefaultView);
    }

    @Test
    void renderDefaultHtmlViewOfB() {
        var result = renderPlugin.render(new B(), CommonMediaType.HTML_UTF8);
        assertTrue(result instanceof com.example.myapp.view.html.BDefaultView);
    }

    @Test
    void renderLowVerbosityJsonViewOfC() {
        var result = renderPlugin.render(new C(12), CommonMediaType.JSON_UTF8, "verbosity-1");
        assertTrue(result instanceof com.example.myapp.view.json.CLowVerbosityView);
    }
}