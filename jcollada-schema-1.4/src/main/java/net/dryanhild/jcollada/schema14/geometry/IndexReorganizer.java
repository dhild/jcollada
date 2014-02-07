package net.dryanhild.jcollada.schema14.geometry;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TObjectIntMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.dryanhild.jcollada.data.geometry.DataType;

public class IndexReorganizer {

    public static final int NO_DATA_INDICATOR = -1;

    private int elementCount;
    private final Map<DataType, TIntList> indicesByElement;
    private final int initialCapacity;

    public IndexReorganizer(int maximumIndexGuess) {
        initialCapacity = maximumIndexGuess;
        indicesByElement = new HashMap<>();
    }

    public int getElementCount() {
        return elementCount;
    }

    public Map<DataType, TIntList> getIndicesByElement() {
        return indicesByElement;
    }

    public int convertToSingleIndex(TObjectIntMap<DataType> elementIndices) {
        Set<DataType> types = elementIndices.keySet();

        int index = 0;
        while (index < elementCount) {
            final int startingIndex = index;

            for (DataType type : types) {
                index = getNextIndexOf(type, elementIndices.get(type), startingIndex);
                if (index != startingIndex) {
                    break;
                }
            }

            if (startingIndex == index) {
                break;
            }
        }

        assert index <= elementCount : "Somehow skipping some indices!";
        if (index == elementCount) {
            elementCount++;
        }

        for (DataType type : types) {
            setIfNoData(type, elementIndices.get(type), index);
        }
        return index;
    }

    private int getNextIndexOf(DataType type, int elementIndex, int startFrom) {
        TIntList list = getList(type);

        int foundAt = list.indexOf(startFrom, elementIndex);
        if (foundAt == -1) {
            foundAt = list.indexOf(startFrom, NO_DATA_INDICATOR);
        }
        if (foundAt == -1) {
            list.add(NO_DATA_INDICATOR);
            return list.size() - 1;
        }
        return foundAt;
    }

    private void setIfNoData(DataType type, int elementIndex, int index) {
        TIntList list = getList(type);

        while (list.size() < (index + 1)) {
            list.add(NO_DATA_INDICATOR);
        }

        list.set(index, elementIndex);
    }

    private TIntList getList(DataType type) {
        TIntList list = indicesByElement.get(type);
        if (list == null) {
            int initialSize = Math.max(initialCapacity, elementCount);
            list = new TIntArrayList(initialSize, NO_DATA_INDICATOR);
            indicesByElement.put(type, list);
        }
        return list;
    }

}
