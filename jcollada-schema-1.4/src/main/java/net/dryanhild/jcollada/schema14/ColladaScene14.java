package net.dryanhild.jcollada.schema14;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.data.Asset;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.geometry.Geometry;

public class ColladaScene14 implements ColladaScene {

    private final Collection<Geometry> geometries = new ArrayList<>();

    @Override
    public Asset getAsset() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Geometry> getGeometries() {
        return geometries;
    }

}
