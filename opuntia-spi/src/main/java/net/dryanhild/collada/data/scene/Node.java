package net.dryanhild.collada.data.scene;

import java.util.List;

import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.transform.Transform;

public interface Node extends AddressableType {

    String getName();

    @Override
    String getId();

    NodeType getType();

    List<Node> getChildren();

    List<Geometry> getGeometries();

    List<Transform> getTransforms();

}
