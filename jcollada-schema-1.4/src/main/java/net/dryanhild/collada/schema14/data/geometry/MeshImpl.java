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
import net.dryanhild.collada.data.geometry.Triangles;
import net.dryanhild.collada.schema14.data.AbstractNameableAddressableType;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;

public class MeshImpl extends AbstractNameableAddressableType implements Geometry {

    private Map<String, Integer> dataCount;
    private Map<String, Integer> dataBytes;
    private Map<String, Integer> interleaveOffset;
    private Triangles triangles;
    private int vertexCount;
    private byte[] vertexData;

    @Override
    public Set<String> getSemantics() {
        return dataCount.keySet();
    }

    @Override
    public int getDataCount(String semantic) {
        return dataCount.get(semantic);
    }

    public void setDataCount(Map<String, Integer> semantics) {
        this.dataCount = semantics;
    }

    @Override
    public int getDataBytes(String semantic) {
        return dataBytes.get(semantic);
    }

    @Override
    public int getInterleaveOffset(String semantic) {
        return interleaveOffset.get(semantic);
    }

    @Override
    public ByteBuffer putInterleavedVertexData(ByteBuffer buffer) {
        return buffer.put(vertexData);
    }

    @Override
    public Triangles getTriangles() {
        return triangles;
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getInterleavedDataSize() {
        int size = 0;
        for (int bytes : dataBytes.values()) {
            size += bytes;
        }
        return size * vertexCount;
    }

    public void setTriangles(Triangles triangles) {
        this.triangles = triangles;
    }

    public void setDataBytes(Map<String, Integer> dataBytes) {
        this.dataBytes = dataBytes;
    }

    public void setInterleaveOffset(Map<String, Integer> interleaveOffset) {
        this.interleaveOffset = interleaveOffset;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public void setVertexData(byte[] bytes) {
        this.vertexData = bytes;
    }
}
