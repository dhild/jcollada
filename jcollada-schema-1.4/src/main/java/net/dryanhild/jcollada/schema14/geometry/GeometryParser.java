package net.dryanhild.jcollada.schema14.geometry;

import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.geometry.data.MeshResult;

import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;

public class GeometryParser {

    public GeometryResult parse(Geometry geom) {
        GeometryResult result = new GeometryResult(geom.getId(), geom.getName());

        if (geom.getMesh() != null) {
            Mesh mesh = geom.getMesh();
            MeshParser parser = new MeshParser(mesh);

            MeshResult parsed = parser.parseMesh();

            result.setMesh(parsed);
        }

        return result;
    }

}
