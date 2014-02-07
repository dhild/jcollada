package net.dryanhild.jcollada.schema14.geometry.data;

import net.dryanhild.jcollada.data.Asset;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.geometry.Mesh;

public class GeometryResult implements Geometry {

    private final String name;
    private final String id;

    private Mesh mesh;

    public GeometryResult(String id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Asset getAsset() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasMesh() {
        return mesh != null;
    }

    @Override
    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

}
