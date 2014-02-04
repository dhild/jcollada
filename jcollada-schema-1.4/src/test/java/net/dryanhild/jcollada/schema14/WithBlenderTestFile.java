package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.geometry.DataType;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.geometry.Mesh;
import net.dryanhild.jcollada.data.geometry.Triangles;

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
        return scene.getGeometryById("Cylinder.001");
    }

    private Mesh getCylinderGeometryMesh() {
        return getCylinderGeometry().getMesh();
    }

    public void cylinderHasNonNullMesh() {
        Geometry cylinder = getCylinderGeometry();

        assert cylinder.hasMesh();
        assert cylinder.getMesh() != null;
    }

    public void cylinderMeshHasPositionsAndNormals() {
        Mesh mesh = getCylinderGeometryMesh();

        assertThat(mesh.getDataTypesUsed()).contains(DataType.POSITION, DataType.NORMAL);
    }

    public void cylinderMeshHasOnly44Triangles() {
        Mesh mesh = getCylinderGeometryMesh();

        assertThat(mesh.getTriangles()).hasSize(1);

        Triangles triangles = mesh.getTriangles().iterator().next();

        assert triangles.getCount() == 44;
        assert triangles.getPrimitiveIndexArray().length == (44 * 3);
    }
}
