package net.dryanhild.jcollada.schema14.geometry;

import net.dryanhild.jcollada.data.geometry.DataType;

public class SourceReference {

    public final DataType type;
    public final String source;

    public SourceReference(DataType type, String source) {
        this.type = type;
        this.source = source;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((source == null) ? 0 : source.hashCode());
        result = (prime * result) + ((type == null) ? 0 : type.hashCode());
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
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

}
