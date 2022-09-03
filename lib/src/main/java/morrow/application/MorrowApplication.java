package morrow.application;

import morrow.config.singleton.Store;
import morrow.config.singleton.loader.CustomSingletonLoader;
import morrow.config.validation.Validation;
import morrow.web.Server;
import morrow.web.endpoint.Router;
import morrow.web.path.UncategorisedSegment;
import morrow.web.protocol.header.FieldName;
import morrow.web.protocol.header.request.AdditionalEncodingsLoader;
import morrow.web.protocol.header.request.FieldNameResolver;
import morrow.web.protocol.header.request.RequestHeaderFieldName;
import morrow.web.protocol.header.request.Map;
import morrow.web.request.Method;
import morrow.web.request.Path;
import morrow.web.request.RawRequest;
import morrow.web.request.Request;
import morrow.web.response.Response;
import morrow.web.view.ControllerRenderPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MorrowApplication {
    private final Store singletonStore;
    private final Server server;
    private final FieldNameResolver fieldNameResolver;


    public MorrowApplication() throws ApplicationException {
        singletonStore = loadSingletons();
        server = new Server(singletonStore);
        fieldNameResolver = loadHeaderFieldNameResolver();
    }

    private FieldNameResolver loadHeaderFieldNameResolver() {
        var additionalEncodings = singletonStore.find(AdditionalEncodingsLoader.class)
                .map(AdditionalEncodingsLoader::additionalEncodings)
                .orElse(java.util.Map.of());
        return FieldNameResolver.builder().encode(additionalEncodings).build();
    }

    private static Store loadSingletons() throws ApplicationException {
        try {
            var singletonStore = new Store();
            singletonStore.put(new Validation(singletonStore));
            singletonStore.put(ControllerRenderPlugin.load(singletonStore));
            singletonStore.put(Router.load(singletonStore));
            CustomSingletonLoader.loadAll(singletonStore).forEach(singletonStore::put);
            return singletonStore;
        } catch (Exception e) {
            throw new SingletonLoadException(e);
        }
    }

    public Response serve(RawRequest rawRequest) {

        Request request = map(rawRequest);

        return server.serve(request);
    }

    private Request map(RawRequest rawRequest) {

        var method = Method.valueOf(rawRequest.method().toUpperCase());

        var segments = Arrays.stream(rawRequest.path().split("/"))
                .filter(s -> !s.isEmpty())
                .map(UncategorisedSegment::new)
                .toList();

        var headersWithEncodedKeys = rawRequest.headers()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> {
                    var rawFieldName = e.getKey();
                    RequestHeaderFieldName<?> fieldName = fieldNameResolver.resolve(rawFieldName);
                    FieldName.Key<?> key = fieldName.key();
                    return key;
                }, java.util.Map.Entry::getValue));

        return new Request(new Path(segments), method, new Map(headersWithEncodedKeys));
    }

    public void onShutdown() {
        try {
            // TODO: shutdown hooks
            singletonStore.close();
        } catch (Exception e) {
            // TODO
            throw new RuntimeException(e);
        }

    }
}
