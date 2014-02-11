package net.dryanhild.collada.schema15.geometry;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class GeometryMesh {

    public Set<String> getSemantics() {
        return ImmutableSet.of();
    }

    public DataDescriptor getDescriptor(String semantic) {
        return null;
    }

    public Iterable<Vertex> getVertices() {
        return null;
    }

    public interface DataDescriptor {

        boolean normalize();

        int elementCount();

        int byteSize();

        Type getType();

        public enum Type {
            BYTE, UNSIGNED_BYTE, SHORT, UNSIGNED_SHORT, INT, UNSIGNED_INT, FLOAT, DOUBLE;
        }

    }

    public interface Vertex {

        byte[] getData(String semantic);

    }

}
