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

package net.dryanhild.collada.schema14;

import net.dryanhild.collada.ColladaLoader;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.geometry.Geometry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "functional")
public class WithGLSLExample {

    private ColladaLoader colladaLoader = new ColladaLoader();
    private URL resourceUrl;
    private ColladaDocument document;

    public WithGLSLExample() {
        colladaLoader.setValidating(false);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        resourceUrl = classLoader.getResource("glsl_example.dae");
    }

    @BeforeMethod
    public void loadDocument() throws IOException, URISyntaxException {
        document = colladaLoader.load(resourceUrl);
    }

    @Test
    public void canLoadTestDae() {
        assertThat(document).isNotNull();
    }

    @Test
    public void correctGeometryExists() {
        assertThat(document.getGeometry("Model_E0_MESH_0_REF_1_lib")).isNotNull();
    }

    @Test(dependsOnMethods = "correctGeometryExists")
    public void geometryHasFiveSemantics() {
        Geometry geometry = document.getGeometry("Model_E0_MESH_0_REF_1_lib");
        assertThat(geometry.getSemantics()).containsOnly("POSITION", "NORMAL", "TEXCOORD", "TANGENT", "BINORMAL");
    }
}
