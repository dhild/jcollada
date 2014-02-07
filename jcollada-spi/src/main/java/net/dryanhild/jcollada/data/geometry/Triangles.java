package net.dryanhild.jcollada.data.geometry;

import java.util.Set;

public interface Triangles {

    String getName();

    int getCount();

    int[] getPrimitiveIndexArray();

    Set<DataType> getDataTypes();

}
