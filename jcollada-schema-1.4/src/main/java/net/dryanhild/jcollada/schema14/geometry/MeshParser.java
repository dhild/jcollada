package net.dryanhild.jcollada.schema14.geometry;

import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private Mesh sourceMesh;
    private Vertices vertices;

    private final List<Triangles> triangles = new ArrayList<>();
    private final Set<DataType> dataTypes = new HashSet<>();
    private final TIntList[] vertexAttributeIndices = new TIntList[DataType.values().length];
    private final TFloatList[] vertexValues = new TFloatList[DataType.values().length];

    public MeshResult parseMesh(Mesh mesh) {
        sourceMesh = mesh;
        vertices = mesh.getVertices();

        gatherDataTypes();

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

    private void gatherDataTypes() {
        addTypes(vertices.getInputArray());

        for (Polylist polys : sourceMesh.getPolylistArray()) {
            addTypes(polys.getInputArray());
        }

        Set<DataType> types = new HashSet<>(dataTypes);
        dataTypes.clear();
        dataTypes.addAll(types);
    }

    private void addTypes(InputLocal[] inputs) {
        for (InputLocal in : inputs) {
            addSemantic(in.getSemantic());
        }
    }

    private void addTypes(InputLocalOffset[] inputs) {
        for (InputLocalOffset in : inputs) {
            addSemantic(in.getSemantic());
        }
    }

    private void addSemantic(String semantic) {
        if (semantic.equals("VERTEX")) {
            return;
        }
        DataType type = DataType.valueOf(semantic);

        dataTypes.add(type);
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

        int[] indices = new int[vertexAttributeIndices.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = -1;
        }
        int[] processedPValues = new int[pValues.size()];

        for (int pIndex = 0; pIndex < pValues.size(); pIndex++) {
            for (DataType type : inputTypes) {
                int index = pValues.get(pIndex + inputOffsets.get(type));
                indices[type.ordinal()] = index;
            }
            processedPValues[pIndex] = getAttributeIndex(indices);
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

    private int getAttributeIndex(int... indices) {
        final int indexCount = vertexAttributeIndices[0].size();
        for (int i = 0; i < indexCount; i++) {
            // search for vertex, use if found.
            boolean found = true;
            for (int j = 0; j < vertexAttributeIndices.length; j++) {
                if (indices[j] != -1) {
                    if (indices[j] != vertexAttributeIndices[j].get(i)) {
                        found = false;
                        break;
                    }
                }
            }
            if (found) {
                return i;
            }
        }

        for (int i = 0; i < vertexAttributeIndices.length; i++) {
            vertexAttributeIndices[i].add(indices[i]);
        }
        return vertexAttributeIndices[0].size() - 1;
    }
}
