package net.dryanhild.collada.schema14.geometry;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TObjectIntMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IndexReorganizer {

    public static final int NO_DATA_INDICATOR = -1;

    private int elementCount;
    private final Map<SourceReference, TIntList> indicesByElement;
    private final int initialCapacity;

    public IndexReorganizer(int maximumIndexGuess) {
        initialCapacity = maximumIndexGuess;
        indicesByElement = new HashMap<>();
    }

    public int getElementCount() {
        return elementCount;
    }

    public Map<SourceReference, TIntList> getIndicesByElement() {
        return indicesByElement;
    }

    public int convertToSingleIndex(TObjectIntMap<SourceReference> elementIndices) {
        Set<SourceReference> sources = elementIndices.keySet();

        int index = 0;
        while (index < elementCount) {
            final int startingIndex = index;

            for (SourceReference source : sources) {
                index = getNextIndexOf(source, elementIndices.get(source), startingIndex);
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

        for (SourceReference source : sources) {
            setIfNoData(source, elementIndices.get(source), index);
        }
        return index;
    }

    private int getNextIndexOf(SourceReference source, int elementIndex, int startFrom) {
        TIntList list = getList(source);

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

    private void setIfNoData(SourceReference source, int elementIndex, int index) {
        TIntList list = getList(source);

        while (list.size() < (index + 1)) {
            list.add(NO_DATA_INDICATOR);
        }

        list.set(index, elementIndex);
    }

    private TIntList getList(SourceReference source) {
        TIntList list = indicesByElement.get(source);
        if (list == null) {
            int initialSize = Math.max(initialCapacity, elementCount);
            list = new TIntArrayList(initialSize, NO_DATA_INDICATOR);
            indicesByElement.put(source, list);
        }
        return list;
    }

}
