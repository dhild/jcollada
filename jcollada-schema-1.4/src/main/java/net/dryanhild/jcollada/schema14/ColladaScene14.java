package net.dryanhild.jcollada.schema14;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.data.Asset;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;

public class ColladaScene14 implements ColladaScene {

    private final Collection<Geometry> geometries = new ArrayList<>();
    private final Collection<Node> nodes = new ArrayList<>();

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
    public <T> T getElementById(String id, Class<T> type) {
        if (Geometry.class.isAssignableFrom(type)) {
            for (Geometry g : geometries) {
                if (id.equals(g.getId())) {
                    return type.cast(g);
                }
            }
            throw new NoSuchElementIdException("Geometry with id " + id + " does not exist!");
        }
        if (Node.class.isAssignableFrom(type)) {
            for (Node n : nodes) {
                if (id.equals(n.getId())) {
                    return type.cast(n);
                }
            }
            throw new NoSuchElementIdException("Node with id " + id + " does not exist!");
        }

        throw new NoSuchElementIdException("No elements of type " + type.getName());
    }

    @Override
    public Collection<Node> getNodes() {
        return nodes;
    }

}
