/*
 * Copyright (c) 2013, D. Ryan Hild <d.ryan.hild@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.dryanhild.jcollada.schema141;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedGeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.Shape3D;

import net.dryanhild.jcollada.LoaderContext;
import net.dryanhild.jcollada.schema141.gen.Geometry;
import net.dryanhild.jcollada.schema141.gen.InputLocal;
import net.dryanhild.jcollada.schema141.gen.InputLocalOffset;
import net.dryanhild.jcollada.schema141.gen.Mesh;
import net.dryanhild.jcollada.schema141.gen.Polygons;
import net.dryanhild.jcollada.schema141.gen.Source;
import net.dryanhild.jcollada.schema141.gen.Triangles;
import net.dryanhild.jcollada.schema141.gen.Vertices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.utils.geometry.GeometryInfo;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class MeshUtil implements GeometryElement {

    final Logger logger = LogManager.getLogger(MeshUtil.class);

    private final String id;
    private final String name;
    private final Mesh mesh;
    private final LoaderContext context;

    public MeshUtil(Geometry geom, LoaderContext context) {
        this.name = geom.getName();
        this.mesh = geom.getMesh();
        this.context = context;
        this.id = geom.getId();

        if (mesh != null) {
            for (Source s : mesh.getSources()) {
                SourceUtil.createSource(s, context);
            }
        }
    }

    private int getVertexFormat(InputLocal input) {
        switch (input.getSemantic()) {
        case "COLOR":
            return GeometryArray.COLOR_3;
        case "NORMAL":
            return GeometryArray.NORMALS;
        case "POSITION":
            return GeometryArray.COORDINATES;
        }
        throw new ParsingErrorException("Can't handle input semantic " + input.getSemantic());
    }

    private int getVertexFormat(InputLocalOffset input) {
        Object obj = context.getObjectById(input.getSource(), Object.class);
        if (obj instanceof Vertices) {
            Vertices vert = (Vertices) obj;
            int format = 0;
            for (InputLocal in : vert.getInputs()) {
                format |= getVertexFormat(in);
            }
            return format;
        }
        SourceUtil s = (SourceUtil) obj;
        switch (input.getSemantic()) {
        case "COLOR":
            switch (s.getSize("R", "G", "B", "A")) {
            case 3:
                return GeometryArray.COLOR_3;
            case 4:
                return GeometryArray.COLOR_4;
            }
            break;
        case "NORMAL":
            return GeometryArray.NORMALS;
        case "VERTEX":
            return GeometryArray.COORDINATES;
        case "TEXCOORD":
            switch (s.getSize("S", "T", "P", "Q")) {
            case 2:
                return GeometryArray.TEXTURE_COORDINATE_2;
            case 3:
                return GeometryArray.TEXTURE_COORDINATE_3;
            case 4:
                return GeometryArray.TEXTURE_COORDINATE_4;
            }
        }
        throw new ParsingErrorException("Can't handle input semantic " + input.getSemantic());
    }

    private int getTexCoordSetCount(List<InputLocalOffset> inputs) {
        int texCoordSetCount = 0;
        for (InputLocalOffset in : inputs) {
            if ("TEXCOORD".equals(in.getSemantic())) {
                texCoordSetCount = Math.max(texCoordSetCount, 1 + in.getSet().intValue());
            }
        }
        return texCoordSetCount;
    }

    private SourceUtil getSource(String id) {
        SourceUtil source = context.getObjectById(id, SourceUtil.class);
        if (source == null) {
            throw new ParsingErrorException("Can't locate source " + id);
        }
        return source;
    }

    private class ColorsData {
        float[] colors3 = null;
        float[] colors4 = null;
        int offset;

        public ColorsData(List<InputLocalOffset> inputs, List<InputLocal> vertices) {
            SourceUtil source = null;
            for (InputLocal in : vertices) {
                if ("COLOR".equals(in.getSemantic())) {
                    source = getSource(in.getSource());
                    for (InputLocalOffset i : inputs) {
                        if ("VERTEX".equals(i.getSemantic())) {
                            offset = i.getOffset().intValue();
                        }
                    }
                }
            }
            if (source == null) {
                for (InputLocalOffset in : inputs) {
                    if ("COLOR".equals(in.getSemantic())) {
                        source = getSource(in.getSource());
                        offset = in.getOffset().intValue();
                    }
                }
            }
            if (source == null) {
                throw new ParsingErrorException("Can't locate COLOR element!");
            }
            int size = source.getSize("R", "G", "B", "A");
            if (size == 3) {
                colors3 = source.getParamsAsFloats("R", "G", "B");
            } else if (size == 4) {
                colors4 = source.getParamsAsFloats("R", "G", "B", "A");
            } else {
                throw new ParsingErrorException("Wrong number of input parameters: " + size);
            }
        }
    }

    private class CoordinatesData {
        double[] coordinates = null;
        int offset;

        public CoordinatesData(List<InputLocalOffset> inputs, List<InputLocal> vertices) {
            for (InputLocalOffset in : inputs) {
                if ("VERTEX".equals(in.getSemantic())) {
                    offset = in.getOffset().intValue();
                }
            }
            for (InputLocal in : vertices) {
                if ("POSITION".equals(in.getSemantic())) {
                    SourceUtil source = getSource(in.getSource());
                    coordinates = source.getParamsAsDoubles("X", "Y", "Z");
                }
            }
        }
    }

    private class NormalsData {
        float[] normals = null;
        int offset;

        public NormalsData(List<InputLocalOffset> inputs, List<InputLocal> vertices) {
            SourceUtil source = null;
            for (InputLocal in : vertices) {
                if ("NORMAL".equals(in.getSemantic())) {
                    source = getSource(in.getSource());
                    normals = source.getParamsAsFloats("X", "Y", "Z");
                    for (InputLocalOffset i : inputs) {
                        if ("VERTEX".equals(i.getSemantic())) {
                            offset = i.getOffset().intValue();
                        }
                    }
                }
            }
            if (source == null) {
                for (InputLocalOffset in : inputs) {
                    if ("NORMAL".equals(in.getSemantic())) {
                        source = getSource(in.getSource());
                        offset = in.getOffset().intValue();
                        normals = source.getParamsAsFloats("X", "Y", "Z");
                    }
                }
            }
        }
    }

    private class TexCoordData {
        float[] texCoord2 = null;
        float[] texCoord3 = null;
        float[] texCoord4 = null;
        int set;
        int offset;

        public TexCoordData(List<InputLocalOffset> inputs, int set) {
            this.set = set;
            SourceUtil source = null;
            for (InputLocalOffset in : inputs) {
                if ("TEXCOORD".equals(in.getSemantic())) {
                    int inSet = in.getSet() == null ? 0 : in.getSet().intValue();
                    if (set == inSet) {
                        offset = in.getOffset().intValue();
                        source = getSource(in.getSource());
                    }
                }
            }
            if (source == null) {
                return;
            }
            int size = source.getSize("S", "T", "P", "Q");
            if (size == 2) {
                texCoord2 = source.getParamsAsFloats("S", "T");
            } else if (size == 3) {
                texCoord3 = source.getParamsAsFloats("S", "T", "P");
            } else if (size == 4) {
                texCoord4 = source.getParamsAsFloats("S", "T", "P", "Q");
            } else {
                throw new ParsingErrorException("Can't parse texCoords with input size " + size);
            }
        }
    }

    private int[] getEvery(int offset, int stride, List<BigInteger> values) {
        int count = values.size() / stride;
        int[] indices = new int[count];
        for (int i = 0; i < count; i++) {
            indices[i] = values.get(offset + (stride * i)).intValue();
        }

        return indices;
    }

    private IndexedGeometryArray processPolygons(Polygons polygons, Vertices vertices) {
        GeometryInfo geomInfo = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

        int texCoordSetCount = getTexCoordSetCount(polygons.getInputs());
        int vertexFormat = 0;
        for (InputLocalOffset input : polygons.getInputs()) {
            vertexFormat |= getVertexFormat(input);
        }

        if ((vertexFormat & GeometryArray.TEXTURE_COORDINATE_2) != 0) {
            geomInfo.setTextureCoordinateParams(texCoordSetCount, 2);
        } else if ((vertexFormat & GeometryArray.TEXTURE_COORDINATE_3) != 0) {
            geomInfo.setTextureCoordinateParams(texCoordSetCount, 3);
        } else if ((vertexFormat & GeometryArray.TEXTURE_COORDINATE_4) != 0) {
            geomInfo.setTextureCoordinateParams(texCoordSetCount, 4);
        }

        CoordinatesData coords = new CoordinatesData(polygons.getInputs(), vertices.getInputs());
        geomInfo.setCoordinates(coords.coordinates);
        ColorsData colors = null;
        NormalsData normals = null;
        TexCoordData texCoords = null;

        if ((vertexFormat & GeometryArray.COLOR_3) != 0) {
            colors = new ColorsData(polygons.getInputs(), vertices.getInputs());
            geomInfo.setColors3(colors.colors3);
        } else if ((vertexFormat & GeometryArray.COLOR_4) != 0) {
            colors = new ColorsData(polygons.getInputs(), vertices.getInputs());
            geomInfo.setColors4(colors.colors4);
        } else if ((vertexFormat & GeometryArray.NORMALS) != 0) {
            normals = new NormalsData(polygons.getInputs(), vertices.getInputs());
            geomInfo.setNormals(normals.normals);
        }

        for (int set = 0; set < texCoordSetCount; set++) {
            texCoords = new TexCoordData(polygons.getInputs(), set);
            if (texCoords.texCoord2 != null) {
                geomInfo.setTextureCoordinates(set, texCoords.texCoord2);
            } else if (texCoords.texCoord3 != null) {
                geomInfo.setTextureCoordinates(set, texCoords.texCoord3);
            } else if (texCoords.texCoord4 != null) {
                geomInfo.setTextureCoordinates(set, texCoords.texCoord4);
            }
        }

        List<List<BigInteger>> ps = new ArrayList<>();
        List<List<BigInteger>> phs = new ArrayList<>();
        for (Object obj : polygons.getPSAndPhs()) {
            // if (obj instanceof PType) {
            // ps.add(((PType)obj).getValues());
            // } else if (obj instanceof PolygonsType.Ph) {
            // PolygonsType.Ph ph = (PolygonsType.Ph)obj;
            // ps.add((ph.getP()).getValues());
            // for (JAXBElement<List<BigInteger>> elem : ph.getHS()) {
            // phs.add(elem.getValue());
            // }
            // }
        }

        List<BigInteger> indices = new ArrayList<>();
        for (List<BigInteger> p : ps) {
            indices.addAll(p);
        }
        for (List<BigInteger> p : phs) {
            indices.addAll(p);
        }

        final int inputCount = polygons.getInputs().size();

        geomInfo.setCoordinateIndices(getEvery(coords.offset, inputCount, indices));

        if ((colors != null) && ((vertexFormat & GeometryArray.COLOR_3) != 0)) {
            geomInfo.setColorIndices(getEvery(colors.offset, inputCount, indices));
        } else if ((colors != null) && ((vertexFormat & GeometryArray.COLOR_4) != 0)) {
            geomInfo.setColorIndices(getEvery(colors.offset, inputCount, indices));
        } else if ((normals != null) && ((vertexFormat & GeometryArray.NORMALS) != 0)) {
            geomInfo.setNormalIndices(getEvery(normals.offset, inputCount, indices));
        }

        if (texCoords != null) {
            for (int set = 0; set < texCoordSetCount; set++) {
                if (texCoords.texCoord2 != null) {
                    geomInfo.setTextureCoordinateIndices(set, getEvery(texCoords.offset, inputCount, indices));
                } else if (texCoords.texCoord3 != null) {
                    geomInfo.setTextureCoordinateIndices(set, getEvery(texCoords.offset, inputCount, indices));
                } else if (texCoords.texCoord4 != null) {
                    geomInfo.setTextureCoordinateIndices(set, getEvery(texCoords.offset, inputCount, indices));
                }
            }
        }

        int[] stripCounts = new int[ps.size() + phs.size()];
        int[] contourCounts = new int[1 + phs.size()];
        int stripOffset = 0;
        for (List<BigInteger> poly : ps) {
            stripCounts[stripOffset++] = poly.size() / inputCount;
        }
        for (List<BigInteger> hole : phs) {
            stripCounts[stripOffset++] = hole.size() / inputCount;
        }

        contourCounts[0] = ps.size();
        stripOffset = 1;
        for (List<BigInteger> hole : phs) {
            contourCounts[stripOffset++] = hole.size() / inputCount;
        }

        geomInfo.setStripCounts(stripCounts);
        geomInfo.setContourCounts(contourCounts);

        return geomInfo.getIndexedGeometryArray();
    }

    private IndexedTriangleArray processTriangles(Triangles triangles, Vertices vertices) {
        int vertexCount = 0;
        for (InputLocal in : vertices.getInputs()) {
            vertexCount = Math.max(vertexCount, getSource(in.getSource()).dataCount);
        }
        for (InputLocalOffset in : triangles.getInputs()) {
            vertexCount = Math.max(vertexCount, getSource(in.getSource()).dataCount);
        }

        int vertexFormat = GeometryArray.COORDINATES;
        for (InputLocal input : vertices.getInputs()) {
            vertexFormat |= getVertexFormat(input);
        }
        for (InputLocalOffset input : triangles.getInputs()) {
            vertexFormat |= getVertexFormat(input);
        }

        int indexCount = triangles.getCount().intValue() * 3;
        int texCoordSetCount = getTexCoordSetCount(triangles.getInputs());
        int[] texCoordSetMap = new int[texCoordSetCount];
        for (int i = 0; i < texCoordSetCount; i++) {
            texCoordSetMap[i] = i;
        }

        IndexedTriangleArray geom = new IndexedTriangleArray(vertexCount, vertexFormat, texCoordSetCount, texCoordSetMap,
                indexCount);

        List<BigInteger> p = triangles.getP();
        final int stride = triangles.getInputs().size();

        {
            CoordinatesData coords = new CoordinatesData(triangles.getInputs(), vertices.getInputs());
            geom.setCoordinates(0, coords.coordinates);
            geom.setCoordinateIndices(0, getEvery(coords.offset, stride, p));
        }
        if ((vertexFormat & GeometryArray.COLOR_3) != 0) {
            ColorsData colors = new ColorsData(triangles.getInputs(), vertices.getInputs());
            geom.setColors(0, colors.colors3);
            geom.setColorIndices(0, getEvery(colors.offset, stride, p));
        } else if ((vertexFormat & GeometryArray.COLOR_4) != 0) {
            ColorsData colors = new ColorsData(triangles.getInputs(), vertices.getInputs());
            geom.setColors(0, colors.colors4);
            geom.setColorIndices(0, getEvery(colors.offset, stride, p));
        }
        if ((vertexFormat & GeometryArray.NORMALS) != 0) {
            NormalsData normals = new NormalsData(triangles.getInputs(), vertices.getInputs());
            geom.setNormals(0, normals.normals);
            geom.setNormalIndices(0, getEvery(normals.offset, stride, p));
        }

        for (int set = 0; set < texCoordSetCount; set++) {
            TexCoordData texCoords = new TexCoordData(triangles.getInputs(), set);
            if (texCoords.texCoord2 != null) {
                geom.setTextureCoordinates(set, 0, texCoords.texCoord2);
                geom.setTextureCoordinateIndices(set, 0, getEvery(texCoords.offset, stride, p));
            } else if (texCoords.texCoord3 != null) {
                geom.setTextureCoordinates(set, 0, texCoords.texCoord3);
                geom.setTextureCoordinateIndices(set, 0, getEvery(texCoords.offset, stride, p));
            } else if (texCoords.texCoord4 != null) {
                geom.setTextureCoordinates(set, 0, texCoords.texCoord4);
                geom.setTextureCoordinateIndices(set, 0, getEvery(texCoords.offset, stride, p));
            }
        }

        return geom;
    }

    @Override
    public Shape3D toShape3D() {
        if (mesh != null) {
            return meshToShape3D();
        }

        throw new ParsingErrorException("Can't handle geometry " + name + ", id: " + id);
    }

    private Shape3D meshToShape3D() {
        List<IndexedGeometryArray> geometries = new ArrayList<>();

        context.addObjectById(mesh.getVertices().getId(), mesh.getVertices());

        for (Object obj : mesh.getLinesAndLinestripsAndPolygons()) {
            if (obj instanceof Triangles) {
                geometries.add(processTriangles((Triangles) obj, mesh.getVertices()));
            } else if (obj instanceof Polygons) {
                geometries.add(processPolygons((Polygons) obj, mesh.getVertices()));
            } else {
                throw new ParsingErrorException("Can't handle type " + obj.getClass().getName());
            }
        }

        Shape3D shape = new Shape3D();
        shape.setName(name);

        for (IndexedGeometryArray geom : geometries) {
            shape.addGeometry(geom);
        }

        return shape;
    }

}
