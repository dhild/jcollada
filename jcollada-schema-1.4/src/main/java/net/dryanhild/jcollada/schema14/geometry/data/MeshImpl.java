package net.dryanhild.jcollada.schema14.geometry.data;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.data.geometry.Mesh;
import net.dryanhild.jcollada.data.geometry.Triangles;

public class MeshImpl implements Mesh {

    private final Collection<Triangles> triangles = new ArrayList<>();

    @Override
    public Collection<DataType> getDataTypesUsed() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float[] getVertexDataOfType(DataType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Triangles> getTriangles() {
        return triangles;
    }

}
