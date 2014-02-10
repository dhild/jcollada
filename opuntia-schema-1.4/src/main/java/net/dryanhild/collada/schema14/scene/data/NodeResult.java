package net.dryanhild.collada.schema14.scene.data;

import java.util.ArrayList;
import java.util.List;

import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.NodeType;
import net.dryanhild.collada.data.transform.Transform;

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

    protected NodeResult(String name, NodeResult copy) {
        this.name = name;
        this.id = copy.id;
        this.type = copy.type;

        children = new ArrayList<>(copy.children);
        geometries = new ArrayList<>(copy.geometries);
        transforms = new ArrayList<>(copy.transforms);
    }

    public void addChild(NodeResult child) {
        children.add(child);
    }

    public void addChildInstance(NodeResult child, String childName) {
        children.add(new NodeResult(childName, child));
    }

    public void addGeometry(Geometry geometry) {
        geometries.add(geometry);
    }

    public void addTransform(Transform transform) {
        transforms.add(transform);
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
