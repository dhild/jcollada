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

package net.dryanhild.collada.schema14.postprocessors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.dryanhild.collada.ParsingException;
import net.dryanhild.collada.schema14.data.geometry.source.FloatArray;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public class VertexList {

    private final List<FloatArray> sourceArrays;
    private final Map<String, FloatArray> semanticsToSources = Maps.newHashMap();
    private final List<int[]> elements = Lists.newArrayList();
    private int[] semanticIndices;

    public VertexList(List<FloatArray> sourceArrays) {
        this.sourceArrays = ImmutableList.copyOf(sourceArrays);
    }

    public void reset(Map<String, Integer> offsets, Map<String, FloatArray> semanticsToSources) {
        semanticIndices = new int[sourceArrays.size()];
        for (String semantic : offsets.keySet()) {
            int offset = offsets.get(semantic);
            FloatArray mapped = semanticsToSources.get(semantic);
            semanticIndices[offset] = sourceArrays.indexOf(mapped);

            if (this.semanticsToSources.containsKey(semantic)) {
                if (!this.semanticsToSources.get(semantic).equals(mapped)) {
                    throw new ParsingException("Unable to handle multiple sources for a single semantic");
                }
            } else {
                this.semanticsToSources.put(semantic, mapped);
            }
        }
    }

    public int insert(int... indices) {
        int[] element = new int[sourceArrays.size()];
        for (int i = 0; i < indices.length; i++) {
            int index = semanticIndices[i];
            element[index] = indices[i];
        }
        int index = indexOf(element);
        if (index >= 0) {
            return index;
        }
        elements.add(element);
        return elements.size() - 1;
    }

    public int indexOf(int... indices) {
        assert indices.length == sourceArrays.size();
        int index = 0;
        for (int[] element : elements) {
            boolean found = true;
            for (int i = 0; i < element.length; i++) {
                if (element[i] != indices[i]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int getOffset(String semantic) {
        FloatArray array = semanticsToSources.get(semantic);
        return sourceArrays.indexOf(array);
    }

    public int getVertexCount() {
        return elements.size();
    }

    public void putElements(ByteBuffer buffer, Map<String, Integer> semanticDataCounts) {
        int[] counts = new int[sourceArrays.size()];
        for (int i = 0; i < counts.length; i++) {
            FloatArray source = sourceArrays.get(i);
            String semantic = null;
            for (String sem : semanticsToSources.keySet()) {
                if (source.equals(semanticsToSources.get(sem))) {
                    semantic = sem;
                    break;
                }
            }
            counts[i] = semanticDataCounts.get(semantic);
        }
        for (int[] element : elements) {
            for (int i = 0; i < element.length; i++) {
                FloatArray source = sourceArrays.get(i);

                int sourceIndex = element[i] * counts[i];
                for (int j = 0; j < counts[i]; j++) {
                    buffer.putFloat(source.get(sourceIndex++));
                }
            }
        }
    }

}
