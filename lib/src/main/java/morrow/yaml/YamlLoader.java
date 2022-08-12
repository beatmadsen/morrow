package morrow.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class YamlLoader<T> {

    private final Yaml yaml;

    public static <U> YamlLoader<U> yielding(Class<U> outputType) {
        return new YamlLoader<>(outputType);
    }

    private YamlLoader(Class<T> outputType) {
        Constructor c = new Constructor(outputType);
        c.setPropertyUtils(new CamelCasePropertyUtils());
        yaml = new Yaml(c);
    }

    public T loadResource(String fileName) throws LoadingException {
        var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new LoadingException("Could not locate file %s in class path resources".formatted(fileName));
        }
        return read(inputStream);
    }

    private T read(InputStream inputStream) throws LoadingException {
        try {
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new LoadingException("Failed to load YAML from InputStream: " + e.getMessage(), e);
        }
    }


}
