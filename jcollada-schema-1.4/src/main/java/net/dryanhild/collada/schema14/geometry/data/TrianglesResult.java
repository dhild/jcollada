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

package net.dryanhild.collada.schema14.geometry.data;

import java.util.Collection;
import java.util.Set;

import net.dryanhild.collada.data.geometry.DataType;
import net.dryanhild.collada.data.geometry.Triangles;

import com.google.common.collect.ImmutableSet;

public class TrianglesResult implements Triangles {

    private final String name;
    private final int count;
    private final int[] indices;
    private final ImmutableSet<DataType> dataTypes;

    public TrianglesResult(String name, int[] indices, Collection<DataType> dataTypes) {
        this.name = name;
        this.count = indices.length / 3;
        this.indices = indices;
        this.dataTypes = ImmutableSet.copyOf(dataTypes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int[] getPrimitiveIndexArray() {
        return indices;
    }

    @Override
    public Set<DataType> getDataTypes() {
        return dataTypes;
    }

}
