package net.dryanhild.jcollada.data.scene;

import java.util.Collection;

import net.dryanhild.jcollada.data.geometry.Geometry;

public interface Node {

    String getName();

    String getId();

    NodeType getType();

    Collection<Node> getChildren();

    Collection<Geometry> getGeometries();

}
