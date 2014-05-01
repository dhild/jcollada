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

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dryanhild.collada.data.geometry.DataType;
import net.dryanhild.collada.data.geometry.Triangles;
import net.dryanhild.collada.schema14.geometry.data.MeshResult;
import net.dryanhild.collada.schema14.geometry.data.TrianglesResult;

import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;
import org.collada.x2005.x11.colladaSchema.PolylistDocument.Polylist;
import org.collada.x2005.x11.colladaSchema.VerticesDocument.Vertices;

public class MeshParser {

    private final Mesh sourceMesh;
    private final Vertices vertices;
    private final SourceStream sourceStream;
    private final IndexReorganizer reorganizer;

    private final List<Triangles> triangles;

    public MeshParser(Mesh mesh) {
        sourceMesh = mesh;
        vertices = mesh.getVertices();
        sourceStream = new SourceStream(mesh.getSourceArray());

        reorganizer = new IndexReorganizer(sourceStream.getMaximumIndex());
        triangles = new ArrayList<>();
    }

    public MeshResult parseMesh() {
        constructTriangles();

        Map<DataType, TFloatList> vertexData = compileVertices();

        return constructResult(vertexData);
    }

    private void constructTriangles() {
        for (Polylist polys : sourceMesh.getPolylistArray()) {
            PolylistParser parser = new PolylistParser(polys, vertices, reorganizer);

            int[] triangleIndices = parser.getTriangleIndices();
            Set<DataType> types = parser.getDataTypes();

            triangles.add(new TrianglesResult(polys.getName(), triangleIndices, types));
        }
    }

    private Map<DataType, TFloatList> compileVertices() {
        Map<DataType, TFloatList> values = new HashMap<>();

        Map<SourceReference, TIntList> indicesByElement = reorganizer.getIndicesByElement();

        for (SourceReference ref : indicesByElement.keySet()) {
            TIntList elementIndices = indicesByElement.get(ref);

            TFloatList floats = values.get(ref.type);
            if (floats == null) {
                int elementSize = sourceStream.getElementSize(ref.source);
                floats = new TFloatArrayList(elementIndices.size() * elementSize);
                values.put(ref.type, floats);
            }

            TIntIterator it = elementIndices.iterator();
            while (it.hasNext()) {
                floats.add(sourceStream.getElement(ref.source, it.next()));
            }
        }

        return values;
    }

    private MeshResult constructResult(Map<DataType, TFloatList> vertexData) {
        MeshResult result = new MeshResult();

        for (DataType type : vertexData.keySet()) {
            TFloatList values = vertexData.get(type);

            result.addVertexData(type, values.toArray());
        }

        result.addTriangles(triangles);

        return result;
    }
}
