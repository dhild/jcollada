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

import net.dryanhild.collada.data.geometry.AttribPointerData;

public class AttribPointerDataImpl implements AttribPointerData {

    private final String semantic;
    private final int size;
    private final int type;
    private final boolean normalized;
    private final int stride;
    private final int offest;

    public AttribPointerDataImpl(String semantic, int size, int type, boolean normalized, int stride, int offest) {
        this.semantic = semantic;
        this.size = size;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;
        this.offest = offest;
    }

    @Override
    public String getSemantic() {
        return semantic;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public boolean isNormalized() {
        return normalized;
    }

    @Override
    public int getStride() {
        return stride;
    }

    @Override
    public int getOffset() {
        return offest;
    }
}
