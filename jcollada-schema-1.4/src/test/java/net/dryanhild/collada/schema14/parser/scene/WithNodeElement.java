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

package net.dryanhild.collada.schema14.parser.scene;

import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.NodeType;
import net.dryanhild.collada.schema14.parser.BaseParserTest;
import net.dryanhild.collada.schema14.postprocessors.geometry.GeometryInstancePostprocessor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WithNodeElement extends BaseParserTest {

    @Inject
    private NodeParser nodeParser;

    private Node node;

    @Override
    protected String getDataString() {
        return "<node id=\"CylinderId\" name=\"Cylinder\" type=\"NODE\">\n" +
               "        <matrix sid=\"transform\">1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1</matrix>\n" +
               "        <instance_geometry url=\"#Cylinder_001-mesh\"/>\n" +
               "      </node>";
    }

    @BeforeMethod
    public void setNode() throws IOException, XmlPullParserException {
        node = nodeParser.parse();
    }

    @Test
    public void nameIsCorrect() {
        assertThat(node.getName()).isEqualTo("Cylinder");
    }

    @Test
    public void idIsCorrect() {
        assertThat(node.getId()).isEqualTo("CylinderId");
    }

    @Test
    public void typeIsCorrect() {
        assertThat(node.getType()).isEqualTo(NodeType.NODE);
    }

    @Test
    public void oneTransformExists() {
        assertThat(node.getTransforms()).hasSize(1);
    }

    @Test
    public void oneGeometryExists() {
        assertThat(node.getGeometries()).hasSize(1);
    }

    @Test
    public void noChildrenExist() {
        assertThat(node.getChildren()).isEmpty();
    }

    @Test
    public void geometryInstancePostprocessorExists() {
        assertThat(data.postprocessors).hasSize(1);
        assertThat(data.postprocessors.get(0)).isInstanceOf(GeometryInstancePostprocessor.class);
    }
}
