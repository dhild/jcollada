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

package net.dryanhild.collada.schema14.parser.geometry;

import net.dryanhild.collada.data.geometry.AttribPointerData;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.schema14.parser.BaseParserTest;
import net.dryanhild.collada.schema14.postprocessors.Postprocessor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WithMeshGeometry extends BaseParserTest {

    public static final String NORMAL = "NORMAL";
    public static final String POSITION = "POSITION";
    @Inject
    private GeometryParser geometryParser;

    private Geometry geometry;

    @Override
    protected String getDataString() {
        return "<geometry id=\"Cylinder_001-mesh\" name=\"Cylinder.001\">\n" +
               "      <mesh>\n" +
               "        <source id=\"Cylinder_001-mesh-positions\">\n" +
               "          <float_array id=\"Cylinder_001-mesh-positions-array\" count=\"72\">3.561967 2 -1 3.561967 2" +
               " 1 4.561967 1.732051 -1 4.561967 1.732051 1 5.294018 1 -1 5.294018 1 1 5.561967 0 -1 5.561967 0 1 5" +
               ".294018 -1 -1 5.294018 -1 1 4.561967 -1.732051 -1 4.561967 -1.732051 1 3.561968 -2 -1 3.561968 -2 1 2" +
               ".561968 -1.732051 -1 2.561968 -1.732051 1 1.829917 -1.000001 -1 1.829917 -1.000001 1 1.561967 -9" +
               ".29825e-7 -1 1.561967 -9.29825e-7 1 1.829916 0.999999 -1 1.829916 0.999999 1 2.561966 1.73205 -1 2" +
               ".561966 1.73205 1</float_array>\n" +
               "          <technique_common>\n" +
               "            <accessor source=\"#Cylinder_001-mesh-positions-array\" count=\"24\" stride=\"3\">\n" +
               "              <param name=\"X\" type=\"float\"/>\n" +
               "              <param name=\"Y\" type=\"float\"/>\n" +
               "              <param name=\"Z\" type=\"float\"/>\n" +
               "            </accessor>\n" +
               "          </technique_common>\n" +
               "        </source>\n" +
               "        <source id=\"Cylinder_001-mesh-normals\">\n" +
               "          <float_array id=\"Cylinder_001-mesh-normals-array\" count=\"132\">0.2588191 0.9659258 0 0" +
               ".7071067 0.7071068 0 0.9659258 0.2588189 0 0.9659258 -0.2588189 0 0.7071067 -0.7071068 0 0.2588191 -0" +
               ".9659258 0 -0.2588189 -0.9659258 0 -0.7071066 -0.707107 0 -0.9659257 -0.2588195 0 -0.965926 0.2588186" +
               " 0 0 0 1 -0.2588194 0.9659258 0 -0.7071073 0.7071062 0 0 0 -1 0.2588191 0.9659258 0 0.7071067 0" +
               ".7071068 0 0.9659258 0.2588189 0 0.9659258 -0.2588189 0 0.7071067 -0.7071068 0 0.2588191 -0.9659258 0" +
               " -0.2588189 -0.9659258 0 -0.7071066 -0.707107 0 -0.9659257 -0.2588195 0 -0.965926 0.2588186 0 0 0 1 0" +
               " 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 -0.2588194 0.9659258 0 -0.7071073 0.7071062 0 0 0 -1 0" +
               " 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1 0 0 -1</float_array>\n" +
               "          <technique_common>\n" +
               "            <accessor source=\"#Cylinder_001-mesh-normals-array\" count=\"44\" stride=\"3\">\n" +
               "              <param name=\"X\" type=\"float\"/>\n" +
               "              <param name=\"Y\" type=\"float\"/>\n" +
               "              <param name=\"Z\" type=\"float\"/>\n" +
               "            </accessor>\n" +
               "          </technique_common>\n" +
               "        </source>\n" +
               "        <vertices id=\"Cylinder_001-mesh-vertices\">\n" +
               "          <input semantic=\"POSITION\" source=\"#Cylinder_001-mesh-positions\"/>\n" +
               "        </vertices>\n" +
               "        <polylist count=\"44\">\n" +
               "          <input semantic=\"VERTEX\" source=\"#Cylinder_001-mesh-vertices\" offset=\"0\"/>\n" +
               "          <input semantic=\"NORMAL\" source=\"#Cylinder_001-mesh-normals\" offset=\"1\"/>\n" +
               "          <vcount>3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3" +
               " 3 3 </vcount>\n" +
               "          <p>0 0 1 0 2 0 2 1 3 1 4 1 4 2 5 2 6 2 6 3 7 3 8 3 8 4 9 4 10 4 10 5 11 5 12 5 12 6 13 6 14" +
               " 6 14 7 15 7 16 7 16 8 17 8 18 8 18 9 19 9 20 9 3 10 1 10 23 10 22 11 23 11 0 11 20 12 21 12 22 12 0 " +
               "13 2 13 22 13 1 14 3 14 2 14 3 15 5 15 4 15 5 16 7 16 6 16 7 17 9 17 8 17 9 18 11 18 10 18 11 19 13 " +
               "19 12 19 13 20 15 20 14 20 15 21 17 21 16 21 17 22 19 22 18 22 19 23 21 23 20 23 3 24 23 24 21 24 5 " +
               "25 3 25 21 25 7 26 5 26 9 26 5 27 21 27 9 27 21 28 19 28 17 28 9 29 21 29 17 29 11 30 9 30 17 30 13 " +
               "31 11 31 15 31 11 32 17 32 15 32 23 33 1 33 0 33 21 34 23 34 22 34 22 35 2 35 20 35 2 36 4 36 20 36 4" +
               " 37 6 37 8 37 20 38 4 38 8 38 18 39 20 39 16 39 20 40 8 40 16 40 8 41 10 41 16 41 10 42 12 42 16 42 " +
               "12 43 14 43 16 43</p>\n" +
               "        </polylist>\n" +
               "      </mesh>\n" +
               "      <extra><technique profile=\"MAYA\"><double_sided>1</double_sided></technique></extra>\n" +
               "    </geometry>" +
               "";
    }

    @BeforeMethod
    public void setGeometryMesh() throws IOException, XmlPullParserException {
        geometry = geometryParser.parse();

        for (Postprocessor p : data.postprocessors) {
            p.process(data);
        }
    }

    @Test
    public void idIsCorrect() {
        assertThat(geometry.getId()).isEqualTo("Cylinder_001-mesh");
    }

    @Test
    public void nameIsCorrect() {
        assertThat(geometry.getName()).isEqualTo("Cylinder.001");
    }

    @Test
    public void dataTypesAreCorrect() {
        assertThat(geometry.getSemantics()).containsOnly(POSITION, NORMAL);
    }

    @Test
    public void positionSizeIs3() {
        assertThat(geometry.getDataCount(POSITION)).isEqualTo(3);
    }

    @Test
    public void normalSizeIs3() {
        assertThat(geometry.getDataCount(NORMAL)).isEqualTo(3);
    }

    @Test
    public void normalsHave3Elements() {
        assertThat(geometry.getDataCount(NORMAL)).isEqualTo(3);
    }

    @Test
    public void positionsHave3Elements() {
        assertThat(geometry.getDataCount(POSITION)).isEqualTo(3);
    }

    @Test
    public void attribDataIsCorrect() {
        List<AttribPointerData> datas = geometry.getAttribPointerData();
        assertThat(datas).extracting("semantic").containsExactly(POSITION, NORMAL);
        assertThat(datas).extracting("size").containsExactly(3, 3);
        assertThat(datas).extracting("type").containsExactly(AttribPointerData.GL_FLOAT, AttribPointerData.GL_FLOAT);
        assertThat(datas).extracting("normalized").containsExactly(false, false);
        assertThat(datas).extracting("stride").containsExactly(2 * 3 * 4, 2 * 3 * 4);
        assertThat(datas).extracting("offset").containsExactly(0, 3 * 4);
    }

    @Test
    public void correctNumberOfTriangles() {
        assertThat(geometry.getTriangles().getCount()).isEqualTo(44);
    }

    @Test
    public void correctTriangleIndices() {
        IntBuffer buffer = IntBuffer.allocate(geometry.getTriangles().getCount() * 3);
        buffer.clear();
        geometry.getTriangles().putElementIndices(buffer);
        buffer.flip();
        assertThat(buffer.remaining()).isEqualTo(buffer.capacity());
        assertThat(buffer.array()).startsWith(0, 1, 2, 3, 4);
    }

    @Test
    public void correctVertexCount() {
        assertThat(geometry.getVertexCount()).isEqualTo(44 * 3);
    }

    @Test
    public void correctInterleavedDataSize() {
        assertThat(geometry.getInterleavedDataSize()).isEqualTo(44 * 3 * 3 * 2 * 4);
    }

    @Test
    public void vertexDataIsRetrieved() {
        ByteBuffer buffer = ByteBuffer.allocate(4 * geometry.getVertexCount() * 3 * 2);
        buffer.clear();
        geometry.putInterleavedVertexData(buffer);
        buffer.flip();
        assertThat(buffer.remaining()).isEqualTo(buffer.capacity());

        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        // Position 0
        assertThat(floatBuffer.get(0)).isEqualTo(3.561967f);
        assertThat(floatBuffer.get(1)).isEqualTo(2f);
        assertThat(floatBuffer.get(2)).isEqualTo(-1f);
        // Normal 0
        assertThat(floatBuffer.get(3)).isEqualTo(0.2588191f);
        assertThat(floatBuffer.get(4)).isEqualTo(0.9659258f);
        assertThat(floatBuffer.get(5)).isEqualTo(0f);
        // Position 1
        assertThat(floatBuffer.get(6)).isEqualTo(3.561967f);
        assertThat(floatBuffer.get(7)).isEqualTo(2f);
        assertThat(floatBuffer.get(8)).isEqualTo(1f);
        // Normal 0
        assertThat(floatBuffer.get(9)).isEqualTo(0.2588191f);
        assertThat(floatBuffer.get(10)).isEqualTo(0.9659258f);
        assertThat(floatBuffer.get(11)).isEqualTo(0f);
    }

}
