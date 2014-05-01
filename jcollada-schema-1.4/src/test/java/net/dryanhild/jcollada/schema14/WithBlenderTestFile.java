/*
 * Copyright (c) 2014 D. Ryan Hild <d.ryan.hild@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import net.dryanhild.collada.ColladaLoader;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.geometry.DataType;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.geometry.Mesh;
import net.dryanhild.collada.data.geometry.Triangles;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "slow")
public class WithBlenderTestFile {

    private static final int TRIANGLE_COUNT = 44;

    ColladaLoader loader;
    ColladaDocument scene;

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

    public void visualSceneHasCylinderNodes() {
        VisualScene visualScene = scene.getVisualScenes().get("#Scene");

        assertThat(visualScene.getNodes()).hasSize(1);
        assertThat(visualScene.getNodes().get(0).getId()).isEqualTo("Cylinder");
    }

    public void cylinderNodesHasCylinderGeometry() {
        Node node = scene.getNodes().get("#Cylinder");

        assertThat(node.getGeometries()).hasSize(1);
        assertThat(node.getGeometries().get(0).getId()).isEqualTo("Cylinder_001-mesh");
    }

    public void mainSceneHasCorrectId() {
        VisualScene mainScene = scene.getMainScene();

        assertThat(mainScene.getId()).isEqualTo("Scene");
    }
}
