package net.dryanhild.jcollada.data.scene;

import java.util.List;

import net.dryanhild.jcollada.data.AddressableType;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.transform.Transform;

public interface Node extends AddressableType {

    String getName();

    @Override
    String getId();

    NodeType getType();

    List<Node> getChildren();

    List<Geometry> getGeometries();

    List<Transform> getTransforms();

}
