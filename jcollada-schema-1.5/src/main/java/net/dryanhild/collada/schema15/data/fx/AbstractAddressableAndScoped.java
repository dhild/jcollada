package net.dryanhild.collada.schema15.data.fx;

import net.dryanhild.collada.data.ScopeAddressableType;
import net.dryanhild.collada.schema15.data.AbstractAddressable;

public abstract class AbstractAddressableAndScoped extends AbstractAddressable implements ScopeAddressableType {

    private final String sid;

    public AbstractAddressableAndScoped(String id, String sid) {
        super(id);
        this.sid = sid;
    }

    @Override
    public String getSID() {
        return sid;
    }

}
