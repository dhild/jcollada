package net.dryanhild.collada.schema14.geometry.data;

import java.util.Collection;
import java.util.Set;

import net.dryanhild.collada.data.geometry.DataType;
import net.dryanhild.collada.data.geometry.Triangles;

import com.google.common.collect.ImmutableSet;

public class TrianglesResult implements Triangles {

    private final String name;
    private final int count;
    private final int[] indices;
    private final ImmutableSet<DataType> dataTypes;

    public TrianglesResult(String name, int[] indices, Collection<DataType> dataTypes) {
        this.name = name;
        this.count = indices.length / 3;
        this.indices = indices;
        this.dataTypes = ImmutableSet.copyOf(dataTypes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int[] getPrimitiveIndexArray() {
        return indices;
    }

    @Override
    public Set<DataType> getDataTypes() {
        return dataTypes;
    }

}
