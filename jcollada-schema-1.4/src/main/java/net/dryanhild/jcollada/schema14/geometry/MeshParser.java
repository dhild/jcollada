package net.dryanhild.jcollada.schema14.geometry;

import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dryanhild.jcollada.ParsingException;
import net.dryanhild.jcollada.data.geometry.DataType;
import net.dryanhild.jcollada.data.geometry.Triangles;
import net.dryanhild.jcollada.schema14.geometry.data.MeshResult;
import net.dryanhild.jcollada.schema14.geometry.data.TrianglesResult;

import org.collada.x2005.x11.colladaSchema.InputLocal;
import org.collada.x2005.x11.colladaSchema.InputLocalOffset;
import org.collada.x2005.x11.colladaSchema.MeshDocument.Mesh;
import org.collada.x2005.x11.colladaSchema.PolylistDocument.Polylist;
import org.collada.x2005.x11.colladaSchema.VerticesDocument.Vertices;

public class MeshParser {

    private static final float NO_DATA_VALUE = Float.NaN;

    private final Mesh sourceMesh;
    private final Vertices vertices;
    private final SourceStream sourceStream;

    private final List<Triangles> triangles;
    private final Set<DataType> dataTypes;
    private final Map<DataType, TIntList> vertexAttributeIndices;
    private final TFloatList[] vertexValues = new TFloatList[DataType.values().length];

    public MeshParser(Mesh mesh) {
        sourceMesh = mesh;
        vertices = mesh.getVertices();
        sourceStream = new SourceStream(mesh.getSourceArray());

        dataTypes = gatherDataTypes();
        triangles = new ArrayList<>();
        vertexAttributeIndices = new HashMap<>();
    }

    public MeshResult parseMesh() {

        constructTriangles();

        return constructResult();
    }

    private MeshResult constructResult() {
        MeshResult result = new MeshResult();

        for (DataType type : dataTypes) {
            float[] data = new float[0];
            result.addData(type, data);
        }

        return result;
    }

    private Set<DataType> gatherDataTypes() {
        Set<DataType> types = new HashSet<>();

        types.addAll(getTypes(vertices.getInputArray()));

        for (Polylist polys : sourceMesh.getPolylistArray()) {
            types.addAll(getTypes(polys.getInputArray()));
        }

        return types;
    }

    private Collection<DataType> getTypes(InputLocal[] inputs) {
        Collection<DataType> types = new ArrayList<>();
        for (InputLocal in : inputs) {
            DataType type = getDataType(in.getSemantic());
            if (type != null) {
                types.add(type);
            }
        }
        return types;
    }

    private Collection<DataType> getTypes(InputLocalOffset[] inputs) {
        Collection<DataType> types = new ArrayList<>();
        for (InputLocalOffset in : inputs) {
            DataType type = getDataType(in.getSemantic());
            if (type != null) {
                types.add(type);
            }
        }
        return types;
    }

    private DataType getDataType(String semantic) {
        if (semantic.equals("VERTEX")) {
            return null;
        }
        return DataType.valueOf(semantic);
    }

    private void constructTriangles() {
        for (Polylist polys : sourceMesh.getPolylistArray()) {
            InputLocalOffset[] inputs = polys.getInputArray();
            List<?> vcounts = polys.getVcount();
            List<?> pValues = polys.getP();
            TObjectIntMap<DataType> inputOffsets = getOffsets(inputs);

            TIntList triangleIndices = getTriangleIndices(vcounts, pValues, inputOffsets);

            int[] singleIndices = attributeIndicesToSingleIndices(triangleIndices, inputOffsets);

            triangles.add(new TrianglesResult(polys.getName(), singleIndices, inputOffsets.keySet()));
        }
    }

    private TIntList getTriangleIndices(List<?> vcounts, List<?> pValues, TObjectIntMap<DataType> inputOffsets) {
        int vertexSize = 0;
        for (int offset : inputOffsets.values()) {
            vertexSize = Math.max(vertexSize, offset);
        }
        vertexSize += 1;

        TIntList triangleIndices = new TIntArrayList(pValues.size());
        int pIndex = 0;
        for (Object obj : vcounts) {
            int vcount = ((BigInteger) obj).intValue();

            if (vcount != 3) {
                throw new ParsingException("Vertex count " + vcount + " is not 3!");
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < vertexSize; j++) {
                    int index = ((BigInteger) pValues.get(pIndex++)).intValue();
                    triangleIndices.add(index);
                }
            }
        }
        return triangleIndices;
    }

    private int[] attributeIndicesToSingleIndices(TIntList pValues, TObjectIntMap<DataType> inputOffsets) {
        Set<DataType> inputTypes = inputOffsets.keySet();

        int[] indices = new int[vertexAttributeIndices.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = -1;
        }
        int[] processedPValues = new int[pValues.size()];

        for (int pIndex = 0; pIndex < pValues.size(); pIndex++) {
            for (DataType type : inputTypes) {
                int index = pValues.get(pIndex + inputOffsets.get(type));
                indices[type.ordinal()] = index;
            }
            // processedPValues[pIndex] = getAttributeIndex(indices);
        }
        return processedPValues;
    }

    private TObjectIntMap<DataType> getOffsets(InputLocalOffset[] inputs) {
        TObjectIntMap<DataType> offsets = new TObjectIntHashMap<>();

        for (InputLocalOffset input : inputs) {
            if (input.getSemantic().equals("VERTEX")) {
                for (InputLocal local : vertices.getInputArray()) {
                    DataType type = DataType.valueOf(local.getSemantic());
                    offsets.put(type, input.getOffset().intValue());
                }
            } else {
                DataType type = DataType.valueOf(input.getSemantic());
                offsets.put(type, input.getOffset().intValue());
            }
        }
        return offsets;
    }

    // private int getAttributeIndex(int... indices) {
    // final int indexCount =
    // vertexAttributeIndices.get(DataType.POSITION).size();
    // for (int i = 0; i < indexCount; i++) {
    // // search for vertex, use if found.
    // boolean found = true;
    // for (int j = 0; j < vertexAttributeIndices.size(); j++) {
    // if (indices[j] != -1) {
    // if (indices[j] != vertexAttributeIndices[j].get(i)) {
    // found = false;
    // break;
    // }
    // }
    // }
    // if (found) {
    // return i;
    // }
    // }
    //
    // for (int i = 0; i < vertexAttributeIndices.length; i++) {
    // vertexAttributeIndices[i].add(indices[i]);
    // }
    // return vertexAttributeIndices[0].size() - 1;
    // }
}
