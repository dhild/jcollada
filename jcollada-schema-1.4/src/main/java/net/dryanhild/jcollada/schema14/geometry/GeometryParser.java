package net.dryanhild.jcollada.schema14.geometry;

import net.dryanhild.jcollada.schema14.geometry.data.GeometryImpl;
import net.dryanhild.jcollada.schema14.geometry.data.MeshImpl;

import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;

public class GeometryParser {

    public GeometryImpl parse(Geometry geom) {
        GeometryImpl result = new GeometryImpl(geom.getId(), geom.getName());

        if (geom.getMesh() != null) {
            result.setMesh(parseMesh(geom.getMesh()));
        }

        return result;
    }

    public MeshImpl parseMesh(Mesh mesh) {
        MeshImpl result = new MeshImpl();

        return result;
    }

}
