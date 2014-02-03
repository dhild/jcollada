package net.dryanhild.jcollada.data;

import java.util.Collection;

import net.dryanhild.jcollada.data.geometry.Geometry;

public interface ColladaScene {

    Asset getAsset();

    Collection<Geometry> getGeometries();

    Geometry getGeometryById(String id);

}
