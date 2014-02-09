package net.dryanhild.jcollada.schema14.scene.data;

import java.util.ArrayList;
import java.util.List;

import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;
import net.dryanhild.jcollada.data.scene.NodeType;
import net.dryanhild.jcollada.data.transform.Transform;

public class NodeResult implements Node {

    private final String name;
    private final String id;
    private final NodeType type;

    private final List<Node> children;
    private final List<Geometry> geometries;
    private final List<Transform> transforms;

    public NodeResult(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = NodeType.valueOf(type);

        children = new ArrayList<>();
        geometries = new ArrayList<>();
        transforms = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public List<Geometry> getGeometries() {
        return geometries;
    }

    @Override
    public List<Transform> getTransforms() {
        return transforms;
    }

}
