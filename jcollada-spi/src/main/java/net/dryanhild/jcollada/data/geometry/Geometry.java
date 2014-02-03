package net.dryanhild.jcollada.data.geometry;

import net.dryanhild.jcollada.data.Asset;

public interface Geometry {

    String getName();

    String getId();

    Asset getAsset();

    boolean hasMesh();

    Mesh getMesh();

}
