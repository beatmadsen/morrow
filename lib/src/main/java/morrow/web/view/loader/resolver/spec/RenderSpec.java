package morrow.web.view.loader.resolver.spec;

import jakarta.validation.constraints.NotBlank;

public record RenderSpec(
        @NotBlank String model,
        @NotBlank String renderer
) {
}
