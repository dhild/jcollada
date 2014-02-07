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

    protected TObjectIntMap<DataType> getMappingPositionNormal(int posIndex, int normalIndex) {
        TObjectIntMap<DataType> mapping = new TObjectIntHashMap<>();

        mapping.put(DataType.POSITION, posIndex);
        mapping.put(DataType.NORMAL, normalIndex);

        return mapping;
    }

    public void firstIndexIsZero() {
        int initialIndex = reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));

        assertThat(initialIndex).isEqualTo(0);
    }

    public void convertIndexThenHasMatchingDataTypes() {
        reorganizer.convertToSingleIndex(getMappingPositionNormal(1, 2));

        assertThat(reorganizer.getIndicesByElement().keySet()).containsOnly(DataType.POSITION, DataType.NORMAL);
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
        TObjectIntMap<DataType> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<DataType> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<DataType> mapping2 = getMappingPositionNormal(2, 2);

        int index0 = reorganizer.convertToSingleIndex(mapping0);
        int index1 = reorganizer.convertToSingleIndex(mapping1);
        int index2 = reorganizer.convertToSingleIndex(mapping2);

        assertThat(index0).isEqualTo(reorganizer.convertToSingleIndex(mapping0));
        assertThat(index1).isEqualTo(reorganizer.convertToSingleIndex(mapping1));
        assertThat(index2).isEqualTo(reorganizer.convertToSingleIndex(mapping2));
    }

    public void threeUniqueIndicesThenHasThreeElements() {
        TObjectIntMap<DataType> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<DataType> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<DataType> mapping2 = getMappingPositionNormal(2, 2);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        assertThat(reorganizer.getElementCount()).isEqualTo(3);
    }

    public void threeUniqueIndicesThenMatchingIndicesByElement() {
        TObjectIntMap<DataType> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<DataType> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<DataType> mapping2 = getMappingPositionNormal(2, 2);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<DataType, TIntList> indices = reorganizer.getIndicesByElement();

        int[] positions = indices.get(DataType.POSITION).toArray();
        int[] normals = indices.get(DataType.NORMAL).toArray();

        assertThat(positions).containsExactly(1, 1, 2);
        assertThat(normals).containsExactly(1, 2, 2);
    }

    public void twoIndicesThenAdditionalDataTypeWithNewIndicesHasThreeElements() {
        TObjectIntMap<DataType> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<DataType> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<DataType> mapping2 = getMappingPositionNormal(2, 2);
        mapping2.put(DataType.COLOR, 3);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<DataType, TIntList> indices = reorganizer.getIndicesByElement();

        assertThat(indices.keySet()).containsOnly(DataType.POSITION, DataType.NORMAL, DataType.COLOR);
    }

    public void twoIndicesThenAdditionalDataTypeWithNewIndicesHasCorrectArrays() {
        TObjectIntMap<DataType> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<DataType> mapping1 = getMappingPositionNormal(1, 2);
        TObjectIntMap<DataType> mapping2 = getMappingPositionNormal(2, 2);
        mapping2.put(DataType.COLOR, 3);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<DataType, TIntList> indices = reorganizer.getIndicesByElement();

        int[] positions = indices.get(DataType.POSITION).toArray();
        int[] normals = indices.get(DataType.NORMAL).toArray();
        int[] colors = indices.get(DataType.COLOR).toArray();

        assertThat(positions).containsExactly(1, 1, 2);
        assertThat(normals).containsExactly(1, 2, 2);
        assertThat(colors).containsExactly(IndexReorganizer.NO_DATA_INDICATOR, IndexReorganizer.NO_DATA_INDICATOR, 3);
    }

    public void twoIndicesThenAdditionalDataTypeWithOldIndicesHasCorrectArrays() {
        TObjectIntMap<DataType> mapping0 = getMappingPositionNormal(1, 1);
        TObjectIntMap<DataType> mapping1 = getMappingPositionNormal(2, 2);
        TObjectIntMap<DataType> mapping2 = getMappingPositionNormal(2, 2);
        mapping2.put(DataType.COLOR, 3);

        reorganizer.convertToSingleIndex(mapping0);
        reorganizer.convertToSingleIndex(mapping1);
        reorganizer.convertToSingleIndex(mapping2);

        Map<DataType, TIntList> indices = reorganizer.getIndicesByElement();

        int[] positions = indices.get(DataType.POSITION).toArray();
        int[] normals = indices.get(DataType.NORMAL).toArray();
        int[] colors = indices.get(DataType.COLOR).toArray();

        assertThat(positions).containsExactly(1, 2);
        assertThat(normals).containsExactly(1, 2);
        assertThat(colors).containsExactly(IndexReorganizer.NO_DATA_INDICATOR, 3);
    }
}
