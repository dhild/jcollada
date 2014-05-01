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

package net.dryanhild.collada.schema14.geometry;

import net.dryanhild.collada.schema14.geometry.data.GeometryResult;
import net.dryanhild.collada.schema14.geometry.data.MeshResult;

import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;

public class GeometryParser {

    private final DefaultLibrary<GeometryResult> library;

    public GeometryParser(DefaultLibrary<GeometryResult> library) {
        this.library = library;
    }

    public void parse(Geometry geom) {
        GeometryResult result = new GeometryResult(geom.getId(), geom.getName());

        if (geom.getMesh() != null) {
            Mesh mesh = geom.getMesh();
            MeshParser parser = new MeshParser(mesh);

            MeshResult parsed = parser.parseMesh();

            result.setMesh(parsed);
        }

        library.add(result);
    }

}
