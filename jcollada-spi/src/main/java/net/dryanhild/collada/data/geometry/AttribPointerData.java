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

/**
 * Contains data values for accessing a specific interleaved data type.
 * <p/>
 * When in doubt, the data returned should be able to be used for calls to glVertexAttribPointer,
 * so consult the official documentation.
 */
public interface AttribPointerData {

    /**
     * Type constant for floating point values.
     * <p/>
     * Part of <code>GL_VERSION_1_1</code>, <code>GL_VERSION_1_0</code>, <code>GL_VERSION_ES_1_0</code>,
     * <code>GL_ES_VERSION_2_0</code>
     */
    int GL_FLOAT = 0x1406;

    /**
     * @return The semantic identifier for this type.
     */
    String getSemantic();

    /**
     * @return The size (in bytes) for this type.
     */
    int getSize();

    /**
     * @return The GL enum value for this data type.
     */
    int getType();

    /**
     * @return Whether the data is normalized.
     */
    boolean isNormalized();

    /**
     * @return The number of bytes to skip in between accesses of this type.
     */
    int getStride();

    /**
     * @return The offset in bytes of the first data element.
     */
    int getOffset();
}
