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

package net.dryanhild.collada.schema14.postprocessors.geometry;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import net.dryanhild.collada.schema14.ParsingData;
import net.dryanhild.collada.schema14.data.geometry.TrianglesHolder;
import net.dryanhild.collada.schema14.data.geometry.Vertices;

public class TrianglesVertexReader {

    private final ParsingData data;
    private final VertexList vertexList;
    private final Vertices vertices;

    private TrianglesHolder trianglesHolder;
    private int vertexSize;
    private TIntList triangleElements;
    private TIntIterator pValues;

    public TrianglesVertexReader(ParsingData data, VertexList vertexList, Vertices vertices) {
        this.data = data;
        this.vertexList = vertexList;
        this.vertices = vertices;
    }

    public TIntList process(TrianglesHolder triangles) {
        reset(triangles);
        while (pValues.hasNext()) {
            handleTriangle();
        }
        return triangleElements;
    }

    private void reset(TrianglesHolder triangles) {
        trianglesHolder = triangles;
        triangleElements = new TIntArrayList(triangles.getCount() * 3);
        pValues = triangles.getP().iterator();

        vertexSize = 0;
        for (String semantic : triangles.getSemantics()) {
            vertexSize = Math.max(vertexSize, triangles.getOffset(semantic));
        }
        vertexSize++;
    }

    private void handleTriangle() {
        for (int i = 0; i < 3; i++) {
            int element = vertexList.insert(nextVertex());
            triangleElements.add(element);
        }
    }

    private int[] nextVertex() {
        int[] vertex = new int[vertexSize];

        for (int i = 0; i < vertexSize; i++) {
            vertex[i] = pValues.next();
        }

        return vertex;
    }
}
