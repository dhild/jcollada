package net.dryanhild.collada.data.geometry;

import net.dryanhild.collada.data.AddressableType;

public interface Geometry extends AddressableType {

    String getName();

    @Override
    String getId();

    boolean hasMesh();

    Mesh getMesh();

}
