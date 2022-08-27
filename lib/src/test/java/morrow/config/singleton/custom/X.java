package morrow.config.singleton.custom;

import morrow.config.singleton.Lookup;
import morrow.config.singleton.ManagedSingleton;

public class X extends ManagedSingleton {
    public X(Lookup singletonLookup) {
        super(singletonLookup);
    }
}
