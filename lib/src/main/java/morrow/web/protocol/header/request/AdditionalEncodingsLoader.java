package morrow.web.protocol.header.request;

import morrow.config.singleton.Lookup;
import morrow.config.singleton.ManagedSingleton;

import java.util.Map;

public abstract class AdditionalEncodingsLoader extends ManagedSingleton {
    public AdditionalEncodingsLoader(Lookup singletonLookup) {
        super(singletonLookup);
    }

    public abstract Map<String, RequestHeaderFieldName<?>> additionalEncodings();
}
