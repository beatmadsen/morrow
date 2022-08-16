package morrow.web.view.loader.resolver;

import morrow.config.Validation;
import morrow.web.view.loader.resolver.spec.RenderSpec;
import morrow.web.view.loader.resolver.spec.SpecLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResolverLoader {
    private final Validation validation;
    private final Map<String, List<RenderSpec>> renderSpecsByUseCase;

    public ResolverLoader(Validation validation, Map<String, List<RenderSpec>> renderSpecsByUseCase) {
        this.validation = validation;
        this.renderSpecsByUseCase = renderSpecsByUseCase;
    }

    public MediaTypeSpecificRendererResolver loadResolver() {
        var renderersByKey = renderSpecsByUseCase
                .entrySet()
                .stream()
                .flatMap(renderers -> {
                    var useCase = renderers.getKey();
                    return resolverEntries(useCase, renderers.getValue());
                }).collect(Collectors.toMap(ResolverEntry::keyTuple, ResolverEntry::rendererClass));

        return new MediaTypeSpecificRendererResolver(renderersByKey);
    }


    private Stream<ResolverEntry> resolverEntries(String useCase, List<RenderSpec> renderSpecs) {
        return renderSpecs.stream()
                .map(renderSpec -> new SpecLoader(validation, renderSpec))
                .peek(SpecLoader::validate)
                .map(SpecLoader::loadClasses)
                .map(vt -> new ResolverEntry(new KeyTuple(useCase, vt.modelClass()), vt.rendererClass()));
    }


}
