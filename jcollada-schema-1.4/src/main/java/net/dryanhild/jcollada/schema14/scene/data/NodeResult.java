package net.dryanhild.jcollada.schema14.scene.data;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;
import net.dryanhild.jcollada.data.scene.NodeType;

public class NodeResult implements Node {

    private final String name;
    private final String id;
    private final NodeType type;

    private final Collection<Node> children;
    private final Collection<Geometry> geometries;

    public NodeResult(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = NodeType.valueOf(type);

        children = new ArrayList<>();
        geometries = new ArrayList<>();
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
    public Collection<Node> getChildren() {
        return children;
    }

    @Override
    public Collection<Geometry> getGeometries() {
        return geometries;
    }

}
