package morrow.web.view.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import morrow.yaml.LoadingException;
import morrow.yaml.YamlLoader;

import java.io.IOException;
import java.util.Map;

public class ViewLoader {

    public Object x() {
        try {
            return new YamlLoader<>(Wrapper.class).loadResource("views.yml");
        } catch (LoadingException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Wrapper {
        SubtypeContainer application;
        SubtypeContainer text;

    }

    public static class SubtypeContainer {

    }


}
