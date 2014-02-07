package net.dryanhild.jcollada.schema14.geometry.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dryanhild.jcollada.data.geometry.DataType;
import net.dryanhild.jcollada.data.geometry.Mesh;
import net.dryanhild.jcollada.data.geometry.Triangles;

public class MeshResult implements Mesh {

    private final Map<DataType, float[]> dataTypes = new EnumMap<>(DataType.class);
    private final List<Triangles> triangles = new ArrayList<>();

    public void addData(DataType type, float[] values) {
        dataTypes.put(type, values);
    }

    @Override
    public Set<DataType> getDataTypesUsed() {
        return dataTypes.keySet();
    }

    @Override
    public float[] getVertexDataOfType(DataType type) {
        return dataTypes.get(type);
    }

    @Override
    public List<Triangles> getTriangles() {
        return triangles;
    }

}
