package morrow.web.view.loader;

import java.io.InputStream;

public class ViewLoader {

    private InputStream viewsFile() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("views.yml");
        if (inputStream == null) {
            // TODO
            throw new RuntimeException("Could not locate views.yml");
        }
        return inputStream;
    }

}
