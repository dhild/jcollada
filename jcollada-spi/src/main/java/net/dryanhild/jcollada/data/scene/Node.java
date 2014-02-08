package net.dryanhild.jcollada.data.scene;

import java.util.Collection;

import net.dryanhild.jcollada.data.geometry.Geometry;

public interface Node {

    String getName();

    String getId();

    Type getType();

    public enum Type {
        JOINT, NODE
    }

    Collection<Node> getChildren();

    Collection<Geometry> getGeometries();

}
