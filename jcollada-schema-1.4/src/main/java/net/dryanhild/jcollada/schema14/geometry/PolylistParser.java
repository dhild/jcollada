package net.dryanhild.jcollada.schema14.geometry;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import net.dryanhild.jcollada.ParsingException;
import net.dryanhild.jcollada.data.geometry.DataType;

import org.collada.x2005.x11.colladaSchema.InputLocal;
import org.collada.x2005.x11.colladaSchema.InputLocalOffset;
import org.collada.x2005.x11.colladaSchema.PolylistDocument.Polylist;
import org.collada.x2005.x11.colladaSchema.VerticesDocument.Vertices;

import com.google.common.collect.ImmutableSet;

public class PolylistParser {

    private final Vertices vertices;
    private final Polylist polys;
    private final IndexReorganizer reorganizer;

    public PolylistParser(Polylist polyList, Vertices vertices, IndexReorganizer reorganizer) {
        polys = polyList;
        this.vertices = vertices;
        this.reorganizer = reorganizer;
    }

    public Set<DataType> getDataTypes() {
        TObjectIntMap<SourceReference> offsets = getOffsets(polys.getInputArray());

        ImmutableSet.Builder<DataType> types = ImmutableSet.builder();

        for (SourceReference ref : offsets.keySet()) {
            types.add(ref.type);
        }
        return types.build();
    }

    public int[] getTriangleIndices() {
        TObjectIntMap<SourceReference> offsets = getOffsets(polys.getInputArray());

        final int vertexStride = vertexStride(offsets);

        TIntList elementIndices = getTriangleIndices(polys.getVcount(), polys.getP(), vertexStride);

        TIntList indices = new TIntArrayList();
        TObjectIntMap<SourceReference> sourceToIndex = new TObjectIntHashMap<>();

        for (int baseOffset = 0; baseOffset < elementIndices.size(); baseOffset += vertexStride) {
            for (SourceReference ref : offsets.keySet()) {
                int elementIndex = elementIndices.get(baseOffset + offsets.get(ref));
                sourceToIndex.put(ref, elementIndex);
            }
            indices.add(reorganizer.convertToSingleIndex(sourceToIndex));
        }

        return indices.toArray();
    }

    private TObjectIntMap<SourceReference> getOffsets(InputLocalOffset[] inputs) {
        TObjectIntMap<SourceReference> offsets = new TObjectIntHashMap<>();

        for (InputLocalOffset input : inputs) {
            if (input.getSemantic().equals("VERTEX")) {
                for (InputLocal local : vertices.getInputArray()) {
                    DataType type = DataType.valueOf(local.getSemantic());
                    SourceReference ref = new SourceReference(type, local.getSource());
                    assert !offsets.containsKey(ref) : "Two input elements reference the same source!";
                    BigInteger offset = input.getOffset();
                    offsets.put(ref, offset == null ? 0 : offset.intValue());
                }
            } else {
                DataType type = DataType.valueOf(input.getSemantic());
                SourceReference ref = new SourceReference(type, input.getSource());
                assert !offsets.containsKey(ref) : "Two input elements reference the same source!";
                BigInteger offset = input.getOffset();
                offsets.put(ref, offset == null ? 0 : offset.intValue());
            }
        }
        return offsets;
    }

    private TIntList getTriangleIndices(List<?> vcounts, List<?> pValues, int vertexStride) {
        TIntList triangleIndices = new TIntArrayList(pValues.size());
        int pIndex = 0;
        for (Object obj : vcounts) {
            int vcount = ((BigInteger) obj).intValue();

            if (vcount != 3) {
                throw new ParsingException("Vertex count " + vcount + " is not 3!");
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < vertexStride; j++) {
                    int index = ((BigInteger) pValues.get(pIndex++)).intValue();
                    triangleIndices.add(index);
                }
            }
        }
        return triangleIndices;
    }

    private int vertexStride(TObjectIntMap<SourceReference> inputOffsets) {
        int vertexSize = 0;
        for (int offset : inputOffsets.values()) {
            vertexSize = Math.max(vertexSize, offset);
        }
        vertexSize += 1;
        return vertexSize;
    }

}
