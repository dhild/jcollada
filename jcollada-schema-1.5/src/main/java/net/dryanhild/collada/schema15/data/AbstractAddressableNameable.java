package net.dryanhild.collada.schema15.data;

import net.dryanhild.collada.data.NameableType;

public abstract class AbstractAddressableNameable extends AbstractAddressable implements NameableType {

    private final String name;

    public AbstractAddressableNameable(String id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
