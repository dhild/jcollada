package net.dryanhild.collada.data.geometry;

import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.Asset;

public interface Geometry extends AddressableType {

    String getName();

    @Override
    String getId();

    Asset getAsset();

    boolean hasMesh();

    Mesh getMesh();

}
