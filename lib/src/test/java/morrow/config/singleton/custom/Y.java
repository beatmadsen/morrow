package morrow.config.singleton.custom;

import morrow.config.singleton.Lookup;
import morrow.config.singleton.ManagedSingleton;

public class Y extends ManagedSingleton {
    public Y(Lookup singletonLookup) {
        super(singletonLookup);
    }
}
