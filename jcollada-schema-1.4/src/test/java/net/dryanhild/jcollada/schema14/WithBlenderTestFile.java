package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.geometry.DataType;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.geometry.Mesh;
import net.dryanhild.jcollada.data.geometry.Triangles;
import net.dryanhild.jcollada.data.scene.Node;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "slow")
public class WithBlenderTestFile {

    private static final int TRIANGLE_COUNT = 44;

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
        return scene.getGeometries().get("#Cylinder_001-mesh");
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

        Map<DataType, float[]> data = mesh.getVertexData();
        assertThat(data.keySet()).contains(DataType.POSITION, DataType.NORMAL);
    }

    public void cylinderMeshHasFloatArrayForPositions() {
        Mesh mesh = getCylinderGeometryMesh();

        Map<DataType, float[]> data = mesh.getVertexData();
        // Test a few of the known points:
        assertThat(data.get(DataType.POSITION)).containsSequence(3.561967f, 2, -1);
        assertThat(data.get(DataType.POSITION)).containsSequence(2, 1, 4.561967f);
        assertThat(data.get(DataType.POSITION)).containsSequence(2.561966f, 1.73205f, 1);
    }

    public void cylinderMeshHasFloatArrayForNormals() {
        Mesh mesh = getCylinderGeometryMesh();

        Map<DataType, float[]> data = mesh.getVertexData();
        // Test a few of the known points:
        assertThat(data.get(DataType.NORMAL)).containsSequence(0.2588191f, 0.9659258f, 0);
        assertThat(data.get(DataType.NORMAL)).containsSequence(0.7071067f, 0.7071068f, 0);
        assertThat(data.get(DataType.NORMAL)).containsSequence(0, 0, -1);
    }

    public void cylinderMeshHasCorrectTriangleCount() {
        Mesh mesh = getCylinderGeometryMesh();

        assertThat(mesh.getTriangles()).hasSize(1);

        Triangles triangles = mesh.getTriangles().iterator().next();

        assertThat(triangles.getCount()).isEqualTo(TRIANGLE_COUNT);
        assertThat(triangles.getPrimitiveIndexArray()).hasSize(TRIANGLE_COUNT * 3);
    }

    @Test(enabled = false)
    public void cylinderNodesHasCylinderGeometry() {
        Geometry geometry = getCylinderGeometry();
        Node node = scene.getNodes().get("#Cylinder");

        assertThat(node.getGeometries()).contains(geometry);
    }
}
