package net.dryanhild.jcollada.schema14;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.NoSuchElementIdException;
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

    @Override
    public Geometry getGeometryById(String id) {
        for (Geometry g : geometries) {
            if (id.equals(g.getName())) {
                return g;
            }
        }

        throw new NoSuchElementIdException("Geometry with id " + id + " does not exist!");
    }

}
