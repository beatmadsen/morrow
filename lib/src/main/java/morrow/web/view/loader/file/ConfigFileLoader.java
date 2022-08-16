package morrow.web.view.loader.file;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.web.view.loader.resolver.spec.RenderSpec;
import morrow.yaml.LoadingException;
import morrow.yaml.YamlLoader;

import java.util.List;
import java.util.Map;

public class ConfigFileLoader {

    private final YamlLoader<Map<String, Map<String, Map<String, List<RenderSpec>>>>> loader;

    public ConfigFileLoader() {
        loader = new YamlLoader<Map<String, Map<String, Map<String, List<RenderSpec>>>>>(new TypeReference<Map<String, Map<String, Map<String, List<RenderSpec>>>>>() {
        });
    }

    public Map<String, Map<String, Map<String, List<RenderSpec>>>> loadViewsFile() {
        try {
            return loader.loadResource("views.yml");
        } catch (LoadingException e) {
            throw new ConfigFileException(e);
        }
    }

}
