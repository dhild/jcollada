package net.dryanhild.jcollada.schema14.geometry;

import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.geometry.data.MeshResult;

import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;

public class GeometryParser {

    public GeometryResult parse(Geometry geom) {
        GeometryResult result = new GeometryResult(geom.getId(), geom.getName());

        if (geom.getMesh() != null) {
            MeshParser parser = new MeshParser();

            Mesh mesh = geom.getMesh();

            MeshResult parsed = parser.parseMesh(mesh);

            result.setMesh(parsed);
        }

        return result;
    }

}
