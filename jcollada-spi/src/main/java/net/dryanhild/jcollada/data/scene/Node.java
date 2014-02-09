package net.dryanhild.jcollada.data.scene;

import java.util.List;

import net.dryanhild.jcollada.data.geometry.Geometry;

public interface Node {

    String getName();

    String getId();

    NodeType getType();

    List<Node> getChildren();

    List<Geometry> getGeometries();

}
