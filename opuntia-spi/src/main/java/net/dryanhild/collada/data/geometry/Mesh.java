package net.dryanhild.collada.data.geometry;

import java.util.List;
import java.util.Map;

public interface Mesh {

    Map<DataType, float[]> getVertexData();

    List<Triangles> getTriangles();

}
