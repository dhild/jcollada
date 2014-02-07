package net.dryanhild.jcollada.data.geometry;

import java.util.List;
import java.util.Set;

public interface Mesh {

    Set<DataType> getDataTypesUsed();

    float[] getVertexDataOfType(DataType type);

    List<Triangles> getTriangles();

}
