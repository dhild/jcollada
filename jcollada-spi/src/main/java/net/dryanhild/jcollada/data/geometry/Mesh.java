package net.dryanhild.jcollada.data.geometry;

import java.util.Collection;

public interface Mesh {

    Collection<DataType> getDataTypesUsed();

    float[] getVertexDataOfType(DataType type);

    Collection<Triangles> getTriangles();

}
