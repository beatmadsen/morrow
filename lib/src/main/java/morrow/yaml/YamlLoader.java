package morrow.yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

public class YamlLoader<T> {
    private final TypeReference<T> outputType;
    private final ObjectMapper mapper;

    public YamlLoader(TypeReference<T> typeReference) {

        this.outputType = typeReference;
        this.mapper = new ObjectMapper(new YAMLFactory());
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
            return mapper.readValue(inputStream, outputType);
        } catch (Exception e) {
            throw new LoadingException("Failed to load YAML from InputStream: " + e.getMessage(), e);
        }
    }


}
