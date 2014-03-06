package net.dryanhild.collada.schema15.data;

import net.dryanhild.collada.data.AddressableType;

public abstract class AbstractAddressable implements AddressableType {

    private final String id;

    public AbstractAddressable(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
