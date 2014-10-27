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

import net.dryanhild.collada.data.fx.Material;
import net.dryanhild.collada.data.geometry.AttribPointerData;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.geometry.GeometryInstance;
import net.dryanhild.collada.data.geometry.Triangles;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

public class GeometryInstanceImpl implements GeometryInstance {

    private Geometry geometry;

    private Material material;

    @Override
    public boolean hasMaterial() {
        return material != null;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Set<String> getSemantics() {
        return geometry.getSemantics();
    }

    @Override
    public int getDataCount(String semantic) {
        return geometry.getDataCount(semantic);
    }

    @Override
    public List<AttribPointerData> getAttribPointerData() {
        return geometry.getAttribPointerData();
    }

    @Override
    public int getVertexCount() {
        return geometry.getVertexCount();
    }

    @Override
    public ByteBuffer putInterleavedVertexData(ByteBuffer buffer) {
        return geometry.putInterleavedVertexData(buffer);
    }

    @Override
    public Triangles getTriangles() {
        return geometry.getTriangles();
    }

    @Override
    public String getId() {
        return geometry.getId();
    }

    @Override
    public String getName() {
        return geometry.getName();
    }

    @Override
    public int getInterleavedDataSize() {
        return geometry.getInterleavedDataSize();
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
