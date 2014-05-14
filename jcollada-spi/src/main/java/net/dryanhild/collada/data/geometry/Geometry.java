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

package net.dryanhild.collada.data.geometry;

import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.NameableType;

import java.nio.ByteBuffer;
import java.util.Set;

/**
 * Generic interface for accessing geometric types. Although a single &gt;geometry&lt; element may contain a
 * combination of constructions (such as triangles, polylists, tristrips, etc.),
 * this interface condenses them together. There is one set of vertex data per geometry,
 * and the indices of the child structures reference these vertices.
 */
public interface Geometry extends AddressableType, NameableType {

    /**
     * Semantic types typically include: POSITION, COLOR, NORMAL, TEXCOORD, etc.
     *
     * @return The semantics for this geometry.
     */
    Set<String> getSemantics();

    /**
     * Gets the number of values for the given semantic.
     * <p/>
     * For example, a 3D position semantic would have 3 values, or an RGBA color would have 4 values.
     *
     * @param semantic The semantic to look up.
     * @return The number of values that the given semantic has in each vertex.
     */
    int getDataCount(String semantic);

    /**
     * Gets the number of bytes used to represent this semantic for a single vertex.
     *
     * @param semantic The semantic to look up.
     * @return The number of bytes that the given semantic has in each vertex.
     */
    int getDataBytes(String semantic);

    /**
     * Gets the offset for the semantic in an interleaved data set.
     *
     * @param semantic The semantic to look up.
     * @return The byte offset that the given semantic has in an interleaved data set.
     */
    int getInterleaveOffset(String semantic);

    /**
     * Gets the number of vertices that this geometry contains.
     *
     * @return The number of vertices.
     */
    int getVertexCount();

    /**
     * Gets the total size in bytes needed to house the interleaved vertex data.
     *
     * @return The size of a buffer needed to house the vertex data.
     */
    int getInterleavedDataSize();

    /**
     * Stores the interleaved vertex data in the given buffer.
     *
     * @param buffer The buffer to store the data in.
     * @return The given buffer (for ease in making chained calls).
     */
    ByteBuffer putInterleavedVertexData(ByteBuffer buffer);

    /**
     * Gets the triangle data elements that are in this Geometry. These Triangles will contain index data that
     * references the vertex data from this Geometry.
     *
     * @return The contained elements that are represented as triangles.
     * @see {@link #putInterleavedVertexData(java.nio.ByteBuffer)}
     */
    Triangles getTriangles();

}
