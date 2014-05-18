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

package net.dryanhild.collada.schema14.postprocessors.geometry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import net.dryanhild.collada.schema14.ParsingData;
import net.dryanhild.collada.schema14.data.geometry.MeshImpl;
import net.dryanhild.collada.schema14.data.geometry.Polylist;
import net.dryanhild.collada.schema14.data.geometry.TrianglesHolder;
import net.dryanhild.collada.schema14.data.geometry.TrianglesImpl;
import net.dryanhild.collada.schema14.data.geometry.Vertices;
import net.dryanhild.collada.schema14.data.geometry.source.FloatArray;
import net.dryanhild.collada.schema14.data.geometry.source.FloatSource;
import net.dryanhild.collada.schema14.postprocessors.Postprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class MeshPostProcessor implements Postprocessor {

    private static final Logger logger = LoggerFactory.getLogger(MeshPostProcessor.class);

    private MeshImpl mesh;

    private VertexList vertexList;
    private PolylistVertexReader polylistVertexReader;
    private TrianglesVertexReader trianglesVertexReader;

    private final List<FloatSource> sources = Lists.newArrayList();
    private Vertices vertices;
    private final List<Polylist> polylists = Lists.newArrayList();
    private final List<TrianglesHolder> triangles = Lists.newArrayList();

    private TIntList triangleElements = new TIntArrayList();

    private final Map<String, Integer> semanticDataCounts = Maps.newHashMap();
    private final Map<String, Integer> semanticDataSizes = Maps.newHashMap();

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

    public void addTriangles(TrianglesHolder triangles) {
        this.triangles.add(triangles);
    }

    @Override
    public void process(ParsingData data) {
        logger.info("Postprocessing mesh '{}', id: {}", mesh.getName(), mesh.getId());
        createReaders(data);

        for (Polylist polys : polylists) {
            processPolylist(polys);
        }
        for (TrianglesHolder triangle : triangles) {
            processTriangles(triangle);
        }

        writeMesh();
        logger.info("Done postprocessing mesh '{}', id: {}", mesh.getName(), mesh.getId());
    }

    private void createReaders(ParsingData data) {
        vertexList = createVertexList();
        polylistVertexReader = new PolylistVertexReader(data, vertexList, vertices);
        trianglesVertexReader = new TrianglesVertexReader(data, vertexList, vertices);
    }

    private VertexList createVertexList() {
        List<FloatArray> arrays = Lists.newArrayList();
        for (FloatSource source : sources) {
            arrays.add(source.getSource());
        }

        return new VertexList(arrays);
    }

    private void processPolylist(Polylist polys) {
        Map<String, Integer> offsets = Maps.newHashMap();
        Map<String, FloatArray> arrays = Maps.newHashMap();
        for (String semantic : polys.getSemantics()) {
            addSemantic(semantic, polys.getSource(semantic));

            if ("VERTEX".equals(semantic)) {
                for (String semantic2 : vertices.getSemantics()) {
                    offsets.put(semantic2, polys.getOffset(semantic));
                    String sourceString = vertices.getSource(semantic2).substring(1);
                    FloatSource source = getSource(sourceString);
                    arrays.put(semantic2, source.getSource());
                }
            } else {
                offsets.put(semantic, polys.getOffset(semantic));
                String sourceString = polys.getSource(semantic).substring(1);
                FloatSource source = getSource(sourceString);
                arrays.put(semantic, source.getSource());
            }
        }
        vertexList.reset(offsets, arrays);
        polylistVertexReader.process(polys);
        triangleElements.addAll(polylistVertexReader.getTriangleElements());
    }

    private void processTriangles(TrianglesHolder trianglesHolder) {
        Map<String, Integer> offsets = Maps.newHashMap();
        Map<String, FloatArray> arrays = Maps.newHashMap();
        for (String semantic : trianglesHolder.getSemantics()) {
            addSemantic(semantic, trianglesHolder.getSource(semantic));

            if ("VERTEX".equals(semantic)) {
                for (String semantic2 : vertices.getSemantics()) {
                    offsets.put(semantic2, trianglesHolder.getOffset(semantic));
                    String sourceString = vertices.getSource(semantic2).substring(1);
                    FloatSource source = getSource(sourceString);
                    arrays.put(semantic2, source.getSource());
                }
            } else {
                offsets.put(semantic, trianglesHolder.getOffset(semantic));
                String sourceString = trianglesHolder.getSource(semantic).substring(1);
                FloatSource source = getSource(sourceString);
                arrays.put(semantic, source.getSource());
            }
        }
        vertexList.reset(offsets, arrays);
        triangleElements.addAll(trianglesVertexReader.process(trianglesHolder));
    }

    private void addSemantic(String semantic, String sourceId) {
        if (semanticDataCounts.containsKey(semantic)) {
            return;
        }
        if (semantic.equals("VERTEX")) {
            for (String semantic2 : vertices.getSemantics()) {
                addSemantic(semantic2, vertices.getSource(semantic2));
            }
        } else {
            FloatSource floatSource = getSource(sourceId.substring(1));
            int count = floatSource.getCommonAccessor().getParamCount();
            semanticDataCounts.put(semantic, count);
            semanticDataSizes.put(semantic, count * 4);
        }
    }

    private FloatSource getSource(String sourceId) {
        for (FloatSource source : sources) {
            if (sourceId.equals(source.getId())) {
                return source;
            }
        }
        throw new NoSuchElementException(sourceId);
    }

    private void writeMesh() {
        mesh.setDataCount(semanticDataCounts);
        mesh.setDataBytes(semanticDataSizes);

        Map<String, Integer> offsets = Maps.newHashMap();
        for (String semantic : semanticDataCounts.keySet()) {
            offsets.put(semantic, vertexList.getOffset(semantic));
        }
        mesh.setInterleaveOffset(offsets);

        mesh.setTriangles(new TrianglesImpl(triangleElements.toArray()));

        mesh.setVertexCount(vertexList.getVertexCount());
        mesh.setVertexData(getVertexData());
    }

    private byte[] getVertexData() {
        ByteBuffer buffer = ByteBuffer.allocate(rawVertexSize()).order(ByteOrder.nativeOrder());

        vertexList.putElements(buffer, semanticDataCounts);

        return buffer.array();
    }

    private int rawVertexSize() {
        int size = 0;
        for (int ds : semanticDataSizes.values()) {
            size += ds;
        }
        size *= vertexList.getVertexCount();
        return size;
    }
}
