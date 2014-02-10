package net.dryanhild.collada.schema14.geometry;

import net.dryanhild.collada.data.geometry.DataType;

import com.google.common.base.Preconditions;

public class SourceReference {

    public final DataType type;
    public final String source;

    public SourceReference(DataType type, String source) {
        Preconditions.checkArgument(type != null, "Data type cannot be null");
        Preconditions.checkArgument(source != null, "Data type cannot be null");
        this.type = type;
        this.source = source;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (source.hashCode());
        result = (prime * result) + (type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SourceReference other = (SourceReference) obj;
        if (!source.equals(other.source)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

}
