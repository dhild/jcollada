package net.dryanhild.collada.schema15.geometry;

public interface DataDescriptor {

    boolean normalize();

    int elementCount();

    int byteSize();

    Type getType();

    public enum Type {
        BYTE, UNSIGNED_BYTE, SHORT, UNSIGNED_SHORT, INT, UNSIGNED_INT, FLOAT, DOUBLE;
    }

}
