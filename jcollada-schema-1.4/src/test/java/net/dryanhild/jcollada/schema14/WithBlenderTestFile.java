package net.dryanhild.jcollada.schema14;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.geometry.Mesh;
import net.dryanhild.jcollada.data.geometry.Mesh.DataType;
import net.dryanhild.jcollada.data.geometry.Triangles;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "slow")
public class WithBlenderTestFile {

    ColladaLoader loader;
    ColladaScene scene;

    @BeforeMethod
    public void reset() {
        loader = new ColladaLoader();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (Reader reader = new InputStreamReader(classLoader.getResourceAsStream("test.dae"))) {
            scene = loader.load(reader);
        } catch (IOException e) {
            assert false : e;
        }
    }

    private Geometry getCylinderGeometry() {
        Collection<Geometry> geometries = scene.getGeometries();

        for (Geometry g : geometries) {
            if ("Cylinder.001".equals(g.getName())) {
                return g;
            }
        }

        return null;
    }

    private Mesh getCylinderGeometryMesh() {
        return getCylinderGeometry().getMesh();
    }

    @Test
    public void cylinderHasNonNullMesh() {
        Geometry cylinder = getCylinderGeometry();

        assert cylinder.hasMesh();
        assert cylinder.getMesh() != null;
    }

    @Test
    public void cylinderMeshHasPositionsAndNormals() {
        Mesh mesh = getCylinderGeometryMesh();

        Assertions.assertThat(mesh.getDataTypesUsed()).contains(DataType.POSITION, DataType.NORMAL);
    }

    @Test
    public void cylinderMeshHasOnly44Triangles() {
        Mesh mesh = getCylinderGeometryMesh();

        Assertions.assertThat(mesh.getTriangles()).hasSize(1);

        Triangles triangles = mesh.getTriangles().iterator().next();

        assert triangles.getCount() == 44;
        assert triangles.getPrimitiveIndexArray().length == (44 * 3);
    }
}
