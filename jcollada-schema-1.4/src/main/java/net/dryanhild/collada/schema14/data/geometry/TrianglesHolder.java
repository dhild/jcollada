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

import com.google.common.collect.Maps;
import gnu.trove.list.TIntList;

import java.util.Map;
import java.util.Set;

public class TrianglesHolder {

    private String name;
    private int count;
    private final Map<String, String> sources = Maps.newHashMap();
    private final Map<String, Integer> offsets = Maps.newHashMap();
    private TIntList p;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addInput(String semantic, String source, int offset) {
        sources.put(semantic, source);
        offsets.put(semantic, offset);
    }

    public Set<String> getSemantics() {
        return sources.keySet();
    }

    public String getSource(String semantic) {
        return sources.get(semantic);
    }

    public int getOffset(String semantic) {
        return offsets.get(semantic);
    }

    public TIntList getP() {
        return p;
    }

    public void setP(TIntList p) {
        this.p = p;
    }
}
