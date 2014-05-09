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

package net.dryanhild.collada.schema14.data.geometry;

import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.geometry.Vertex;
import net.dryanhild.collada.schema14.data.AbstractNameableAddressableType;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MeshImpl extends AbstractNameableAddressableType implements Geometry {

    private Map<String, Integer> semantics;
    private int vertexCount;
    private Iterable<Vertex> vertices;

    @Override
    public Set<String> getSemantics() {
        return semantics.keySet();
    }

    @Override
    public int getDataSize(String semantic) {
        return semantics.get(semantic);
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return vertices.iterator();
    }

    public void setSemantics(Map<String, Integer> semantics) {
        this.semantics = semantics;
    }

    public void setVertices(int count, Iterable<Vertex> vertices) {
        vertexCount = count;
        this.vertices = vertices;
    }
}
