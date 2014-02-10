package net.dryanhild.collada.data.geometry;

import java.util.Set;

public interface Triangles {

    String getName();

    int getCount();

    int[] getPrimitiveIndexArray();

    Set<DataType> getDataTypes();

}
