package net.dryanhild.jcollada.schema14.geometry;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import net.dryanhild.collada.data.geometry.DataType;
import net.dryanhild.collada.data.geometry.Triangles;
import net.dryanhild.collada.schema14.geometry.MeshParser;
import net.dryanhild.collada.schema14.geometry.data.MeshResult;

import org.apache.xmlbeans.XmlException;
import org.collada.x2005.x11.colladaSchema.MeshDocument;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;
import org.testng.annotations.Test;

@Test
public class MeshParserTest {

    private static final String MESH_XML = //
    "      <mesh xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n"
            + "        <source id=\"Cylinder_001-mesh-positions\">\n"
            + "          <float_array id=\"Cylinder_001-mesh-positions-array\" count=\"72\">"
            + "3.561967 2 -1 3.561967 2 1 4.561967 1.732051 -1 4.561967 1.732051 1 5.294018 1 -1 5.294018 1 1 "
            + "5.561967 0 -1 5.561967 0 1 5.294018 -1 -1 5.294018 -1 1 4.561967 -1.732051 -1 4.561967 -1.732051 1 "
            + "3.561968 -2 -1 3.561968 -2 1 2.561968 -1.732051 -1 2.561968 -1.732051 1 1.829917 -1.000001 -1 "
            + "1.829917 "
            + "-1.000001 1 1.561967 -9.29825e-7 -1 1.561967 -9.29825e-7 1 1.829916 0.999999 -1 1.829916 0.999999 1 "
            + "2.561966 1.73205 -1 2.561966 1.73205 1</float_array>\n"
            + "          <technique_common>\n"
            + "            <accessor source=\"#Cylinder_001-mesh-positions-array\" count=\"24\" stride=\"3\">\n"
            + "              <param name=\"X\" type=\"float\"/>\n"
            + "              <param name=\"Y\" type=\"float\"/>\n"
            + "              <param name=\"Z\" type=\"float\"/>\n" //
            + "            </accessor>\n" //
            + "          </technique_common>\n" //
            + "        </source>\n" //
            + "        <source id=\"Cylinder_001-mesh-normals\">\n"
            + "          <float_array id=\"Cylinder_001-mesh-normals-array\" count=\"132\">0.2588191 0.9659258 0 "
            + "0.7071067 0.7071068 0 0.9659258 0.2588189 0 0.9659258 -0.2588189 0 0.7071067 -0.7071068 0 0.2588191 "
            + "-0.9659258 0 -0.2588189 -0.9659258 0 -0.7071066 -0.707107 0 -0.9659257 -0.2588195 0 -0.965926 "
            + "0.2588186 0 0 0 1 -0.2588194 0.9659258 0 -0.7071073 0.7071062 0 0 0 -1 0.2588191 0.9659258 0 "
            + "0.7071067 0.7071068 0 0.9659258 0.2588189 0 0.9659258 -0.2588189 0 0.7071067 -0.7071068 0 0.2588191 "
            + "-0.9659258 0 -0.2588189 -0.9659258 0 -0.7071066 -0.707107 0 -0.9659257 -0.2588195 0 -0.965926 "
            + "0.2588186 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 -0.2588194 0.9659258 0 -0.7071073 "
            + "0.7071062 0 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1</float_array>\n"
            + "          <technique_common>\n"
            + "            <accessor source=\"#Cylinder_001-mesh-normals-array\" count=\"44\" stride=\"3\">\n"
            + "              <param name=\"X\" type=\"float\"/>\n"
            + "              <param name=\"Y\" type=\"float\"/>\n"
            + "              <param name=\"Z\" type=\"float\"/>\n" + "            </accessor>\n"
            + "          </technique_common>\n" + "        </source>\n"
            + "        <vertices id=\"Cylinder_001-mesh-vertices\">\n"
            + "          <input semantic=\"POSITION\" source=\"#Cylinder_001-mesh-positions\"/>\n"
            + "        </vertices>\n" + "        <polylist count=\"44\">\n"
            + "          <input semantic=\"VERTEX\" source=\"#Cylinder_001-mesh-vertices\" offset=\"0\"/>\n"
            + "          <input semantic=\"NORMAL\" source=\"#Cylinder_001-mesh-normals\" offset=\"1\"/>\n"
            + "          <vcount>3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 "
            + "3 3 3 3 </vcount>\n"
            + "          <p>0 0 1 0 2 0 2 1 3 1 4 1 4 2 5 2 6 2 6 3 7 3 8 3 8 4 9 4 10 4 10 5 11 5 12 5 12 6 13 "
            + "6 14 6 14 7 15 7 16 7 16 8 17 8 18 8 18 9 19 9 20 9 3 10 1 10 23 10 22 11 23 11 0 11 20 12 21 12 "
            + "22 12 0 13 2 13 22 13 1 14 3 14 2 14 3 15 5 15 4 15 5 16 7 16 6 16 7 17 9 17 8 17 9 18 11 18 10 "
            + "18 11 19 13 19 12 19 13 20 15 20 14 20 15 21 17 21 16 21 17 22 19 22 18 22 19 23 21 23 20 23 3 24 "
            + "23 24 21 24 5 25 3 25 21 25 7 26 5 26 9 26 5 27 21 27 9 27 21 28 19 28 17 28 9 29 21 29 17 29 11 "
            + "30 9 30 17 30 13 31 11 31 15 31 11 32 17 32 15 32 23 33 1 33 0 33 21 34 23 34 22 34 22 35 2 35 20 "
            + "35 2 36 4 36 20 36 4 37 6 37 8 37 20 38 4 38 8 38 18 39 20 39 16 39 20 40 8 40 16 40 8 41 10 41 "
            + "16 41 10 42 12 42 16 42 12 43 14 43 16 43</p>\n" //
            + "        </polylist>\n" //
            + "      </mesh>";

    protected Mesh getMesh(String source) {
        try {
            MeshDocument doc = MeshDocument.Factory.parse(source);
            return doc.getMesh();
        } catch (XmlException e) {
            throw new RuntimeException(e);
        }
    }

    public void meshHasTwoDataTypes() {
        Mesh mesh = getMesh(MESH_XML);
        MeshParser parser = new MeshParser(mesh);

        MeshResult result = parser.parseMesh();

        Map<DataType, float[]> data = result.getVertexData();

        assertThat(data.keySet()).containsOnly(DataType.POSITION, DataType.NORMAL);
    }

    @Test(dependsOnMethods = "meshHasTwoDataTypes")
    public void dataArraysHaveCorrectSize() {
        Mesh mesh = getMesh(MESH_XML);
        MeshParser parser = new MeshParser(mesh);

        MeshResult result = parser.parseMesh();

        Map<DataType, float[]> data = result.getVertexData();

        assertThat(data.get(DataType.POSITION)).hasSize(44 * 3 * 3);
        assertThat(data.get(DataType.NORMAL)).hasSize(44 * 3 * 3);
    }

    public void meshHasOneTriangle() {
        Mesh mesh = getMesh(MESH_XML);
        MeshParser parser = new MeshParser(mesh);

        MeshResult result = parser.parseMesh();

        assertThat(result.getTriangles()).hasSize(1);
    }

    @Test(dependsOnMethods = { "meshHasOneTriangle", "dataArraysHaveCorrectSize" })
    public void firstVertexIsCorrect() {
        Mesh mesh = getMesh(MESH_XML);
        MeshParser parser = new MeshParser(mesh);

        MeshResult result = parser.parseMesh();

        Triangles triangles = result.getTriangles().get(0);
        Map<DataType, float[]> data = result.getVertexData();
        float[] positions = data.get(DataType.POSITION);
        float[] normals = data.get(DataType.NORMAL);

        assertThat(triangles.getPrimitiveIndexArray()).startsWith(0);
        assertThat(positions).startsWith(3.561967f, 2, -1);
        assertThat(normals).startsWith(0.2588191f, 0.9659258f, 0);
    }

    @Test(dependsOnMethods = { "meshHasOneTriangle", "dataArraysHaveCorrectSize" })
    public void someInteriorElementsExist() {
        Mesh mesh = getMesh(MESH_XML);
        MeshParser parser = new MeshParser(mesh);

        MeshResult result = parser.parseMesh();

        Map<DataType, float[]> data = result.getVertexData();
        float[] positions = data.get(DataType.POSITION);
        float[] normals = data.get(DataType.NORMAL);

        assertThat(positions).contains(2.561966f, 1.73205f, 1);
        assertThat(normals).contains(1, -0.2588194f, 0.9659258f);
    }
}
