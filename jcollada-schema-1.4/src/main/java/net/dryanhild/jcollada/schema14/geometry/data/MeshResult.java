package net.dryanhild.jcollada.schema14.geometry.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.dryanhild.jcollada.data.geometry.DataType;
import net.dryanhild.jcollada.data.geometry.Mesh;
import net.dryanhild.jcollada.data.geometry.Triangles;

import com.google.common.collect.ImmutableMap;

public class MeshResult implements Mesh {

    private final Map<DataType, float[]> dataTypes = new EnumMap<>(DataType.class);
    private final List<Triangles> triangles = new ArrayList<>();

    public void addVertexData(DataType type, float[] value) {
        dataTypes.put(type, value);
    }

    public void addTriangles(Collection<Triangles> triangleCollection) {
        this.triangles.addAll(triangleCollection);
    }

    @Override
    public Map<DataType, float[]> getVertexData() {
        return ImmutableMap.copyOf(dataTypes);
    }

    @Override
    public List<Triangles> getTriangles() {
        return triangles;
    }

}
