package net.dryanhild.jcollada.schema14.scene.data;

import java.util.ArrayList;
import java.util.List;

import net.dryanhild.jcollada.data.scene.VisualScene;

public class VisualSceneResult implements VisualScene {

    private final String id;

    private final List<NodeResult> nodes;

    public VisualSceneResult(String id) {
        this.id = id;
        nodes = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    public void addNode(NodeResult node) {
        nodes.add(node);
    }

    @Override
    public List<NodeResult> getNodes() {
        return nodes;
    }

}
