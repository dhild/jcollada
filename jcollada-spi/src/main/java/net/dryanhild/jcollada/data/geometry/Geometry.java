package net.dryanhild.jcollada.data.geometry;

import net.dryanhild.jcollada.data.AddressableType;
import net.dryanhild.jcollada.data.Asset;

public interface Geometry extends AddressableType {

    String getName();

    @Override
    String getId();

    Asset getAsset();

    boolean hasMesh();

    Mesh getMesh();

}
