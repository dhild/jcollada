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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.schema14.ParsingData;
import net.dryanhild.collada.schema14.data.geometry.MeshImpl;
import net.dryanhild.collada.schema14.data.geometry.Polylist;
import net.dryanhild.collada.schema14.data.geometry.Vertices;
import net.dryanhild.collada.schema14.data.geometry.source.FloatSource;

import java.util.List;
import java.util.Map;

public class MeshPostProcessor implements Postprocessor {

    private MeshImpl mesh;

    private PolylistVertexReader polylistVertexReader;

    private final List<FloatSource> sources = Lists.newArrayList();
    private Vertices vertices;
    private final List<Polylist> polylists = Lists.newArrayList();

    private final Map<String, Integer> semantics = Maps.newHashMap();

    public MeshPostProcessor(MeshImpl mesh) {
        this.mesh = mesh;
    }

    public void addSource(FloatSource source) {
        sources.add(source);
    }

    public void setVertices(Vertices vertices) {
        this.vertices = vertices;
    }

    public void addPolylist(Polylist polylist) {
        polylists.add(polylist);
    }

    @Override
    public void process(ParsingData data) {
        polylistVertexReader = new PolylistVertexReader(data);
        for (Polylist polys : polylists) {
            processPolylist(polys);
        }

        writeMesh();
    }

    private void processPolylist(Polylist polys) {
        for (String semantic : polys.getSemantics()) {
            addSemantic(semantic, polys.getSource(semantic));
        }
    }

    private void addSemantic(String semantic, String sourceId) {
        if (semantics.containsKey(semantic)) {
            return;
        }
        if (semantic.equals("VERTEX")) {
            for (String semantic2 : vertices.getSemantics()) {
                addSemantic(semantic2, vertices.getSource(semantic2));
            }
        } else {
            FloatSource floatSource = getSource(sourceId.substring(1));
            int count = floatSource.getCommonAccessor().getParamCount();
            semantics.put(semantic, count);
        }
    }

    private FloatSource getSource(String sourceId) {
        for (FloatSource source : sources) {
            if (sourceId.equals(source.getId())) {
                return source;
            }
        }
        throw new NoSuchElementIdException(sourceId);
    }

    private void writeMesh() {
        mesh.setDataCount(semantics);
    }
}
