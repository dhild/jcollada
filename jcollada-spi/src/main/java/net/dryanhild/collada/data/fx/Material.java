package net.dryanhild.collada.data.fx;

import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.NameableType;
import net.dryanhild.collada.data.ScopeAddressableType;

public interface Material extends AddressableType, NameableType {

    Iterable<? extends ScopeAddressableType> getParameters();

    Effect getEffect();

}
