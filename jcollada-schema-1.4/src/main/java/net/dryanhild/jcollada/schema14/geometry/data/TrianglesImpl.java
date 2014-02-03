package net.dryanhild.jcollada.schema14.geometry.data;

import net.dryanhild.jcollada.data.geometry.Triangles;

public class TrianglesImpl implements Triangles {

    private final String name;
    private final int count;
    private final int[] indices;

    public TrianglesImpl(String name, int count) {
        this.name = name;
        this.count = count;
        this.indices = new int[count * 3];
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

}
