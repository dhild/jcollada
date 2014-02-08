package net.dryanhild.jcollada.schema14.scene;

import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.collada.x2005.x11.colladaSchema.NodeType;

public class NodeParser {

    private final Node node;

    public NodeParser(Node node) {
        this.node = node;
    }

    public NodeResult parse() {
        NodeType.Enum typeEnum = node.getType();
        String type = typeEnum == null ? "NODE" : typeEnum.toString();
        NodeResult result = new NodeResult(node.getName(), node.getId(), type);

        return result;
    }

}
