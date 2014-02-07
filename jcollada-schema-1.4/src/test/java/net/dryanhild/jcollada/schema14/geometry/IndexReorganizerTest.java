package net.dryanhild.jcollada.schema14.geometry;

import static org.assertj.core.api.Assertions.assertThat;
import gnu.trove.list.TIntList;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.Map;

import net.dryanhild.jcollada.data.geometry.DataType;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Test
public class IndexReorganizerTest {

    private static final SourceReference COLORS = new SourceReference(DataType.COLOR, "normals");
    private static final SourceReference NORMALS = new SourceReference(DataType.NORMAL, "normals");
    private static final SourceReference POSITIONS = new SourceReference(DataType.POSITION, "positions");

    @DataProvider
    public static Object[][] initialSizes() {
        return new Object[][] { { Integer.valueOf(0) }, { Integer.valueOf(1) }, { Integer.valueOf(25) }, };
    }

    private final int initialCapacity;
    private IndexReorganizer reorganizer;

    @Factory(dataProvider = "initialSizes")
    public IndexReorganizerTest(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    @BeforeMethod()
    public void resetReorganizer() {
        reorganizer = new IndexReorganizer(initialCapacity);
    }

    protected TObjectIntMap<SourceReference> getMappingPositionNormal(int posIndex, int normalIndex) {
        TObjectIntMap<SourceReference> mapping = new TObjectIntHashMap<>();

        mapping.put(POSITIONS, posIndex);
        mapping.put(NORMALS, normalIndex);

        return mapping;
    }

    public void firstIndexIsZero() {
        int initialIndex = reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));

        assertThat(initialIndex).isEqualTo(0);
    }

    public void convertIndexThenHasMatchingDataTypes() {
        reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));

        assertThat(reorganizer.getIndicesByElement().keySet()).containsOnly(POSITIONS, NORMALS);
    }

    public void duplicateIndicesHaveSameValue() {
        int initialIndex = reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));
        int secondIndex = reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));

        assertThat(initialIndex).isEqualTo(secondIndex);
    }

    public void secondUniqueIndexIsOne() {
        reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));
        int index = reorganizer.convertToSingleIndex(getMappingPositionNormal(2, 2));

        assertThat(index).isEqualTo(1);
    }

    public void secondUniqueIndexIsTwoWithDuplicateFirst() {
        reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));
        reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));
        int index = reorganizer.convertToSingleIndex(getMappingPositionNormal(2, 2));

        assertThat(index).isEqualTo(1);
    }

    public void threeUniqueIndicesAreUnique() {
        int index0 = reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));
        int index1 = reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 1));
        int index2 = reorganizer.convertToSingleIndex(getMappingPositionNormal(2, 2));

        assertThat(index0).isEqualTo(0);
        assertThat(index1).isEqualTo(1);
        assertThat(index2).isEqualTo(2);
    }

    public void threeUniqueIndicesThenDuplicatesMatch() {
        TObjectIntMap<SourceReference> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<SourceReference> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<SourceReference> mapping2 = getMappingPositionNormal(2, 2);

        int index0 = reorganizer.convertToSingleIndex(mapping0);
        int index1 = reorganizer.convertToSingleIndex(mapping1);
        int index2 = reorganizer.convertToSingleIndex(mapping2);

        assertThat(index0).isEqualTo(reorganizer.convertToSingleIndex(mapping0));
        assertThat(index1).isEqualTo(reorganizer.convertToSingleIndex(mapping1));
        assertThat(index2).isEqualTo(reorganizer.convertToSingleIndex(mapping2));
    }

    public void threeUniqueIndicesThenHasThreeElements() {
        TObjectIntMap<SourceReference> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<SourceReference> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<SourceReference> mapping2 = getMappingPositionNormal(2, 2);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        assertThat(reorganizer.getElementCount()).isEqualTo(3);
    }

    public void threeUniqueIndicesThenMatchingIndicesByElement() {
        TObjectIntMap<SourceReference> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<SourceReference> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<SourceReference> mapping2 = getMappingPositionNormal(2, 2);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<SourceReference, TIntList> indices = reorganizer.getIndicesByElement();

        int[] positions = indices.get(POSITIONS).toArray();
        int[] normals = indices.get(NORMALS).toArray();

        assertThat(positions).containsExactly(1, 1, 2);
        assertThat(normals).containsExactly(1, 2, 2);
    }

    public void twoIndicesThenAdditionalDataTypeWithNewIndicesHasThreeElements() {
        TObjectIntMap<SourceReference> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<SourceReference> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<SourceReference> mapping2 = getMappingPositionNormal(2, 2);
        mapping2.put(COLORS, 3);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<SourceReference, TIntList> indices = reorganizer.getIndicesByElement();

        assertThat(indices.keySet()).containsOnly(POSITIONS, NORMALS, COLORS);
    }

    public void twoIndicesThenAdditionalDataTypeWithNewIndicesHasCorrectArrays() {
        TObjectIntMap<SourceReference> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<SourceReference> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<SourceReference> mapping2 = getMappingPositionNormal(2, 2);
        mapping2.put(COLORS, 3);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<SourceReference, TIntList> indices = reorganizer.getIndicesByElement();

        int[] positions = indices.get(POSITIONS).toArray();
        int[] normals = indices.get(NORMALS).toArray();
        int[] colors = indices.get(COLORS).toArray();

        assertThat(positions).containsExactly(1, 1, 2);
        assertThat(normals).containsExactly(1, 2, 2);
        assertThat(colors).containsExactly(IndexReorganizer.NO_DATA_INDICATOR, IndexReorganizer.NO_DATA_INDICATOR, 3);
    }

    public void twoIndicesThenAdditionalDataTypeWithOldIndicesHasCorrectArrays() {
        TObjectIntMap<SourceReference> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<SourceReference> mapping1 = getMappingPositionNormal(2, 2);
        TObjectIntMap<SourceReference> mapping2 = getMappingPositionNormal(2, 2);
        mapping2.put(COLORS, 3);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<SourceReference, TIntList> indices = reorganizer.getIndicesByElement();

        int[] positions = indices.get(POSITIONS).toArray();
        int[] normals = indices.get(NORMALS).toArray();
        int[] colors = indices.get(COLORS).toArray();

        assertThat(positions).containsExactly(1, 2);
        assertThat(normals).containsExactly(1, 2);
        assertThat(colors).containsExactly(IndexReorganizer.NO_DATA_INDICATOR, 3);
    }
}
