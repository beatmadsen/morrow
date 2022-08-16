package morrow.web.view.loader.resolver;

import morrow.web.view.Renderer;

record ResolverEntry(KeyTuple keyTuple, Class<? extends Renderer<?, ?>> rendererClass) {

}
