package net.dryanhild.jcollada.schema14.scene;

import net.dryanhild.jcollada.schema14.scene.data.MatrixTransform;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.InstanceWithExtra;
import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;
import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.collada.x2005.x11.colladaSchema.NodeType;

public class NodeParser {

    public NodeResult parse(Node node, NodeLibrary library) {
        NodeType.Enum typeEnum = node.getType();
        String type = typeEnum.toString();
        NodeResult result = new NodeResult(node.getName(), node.getId(), type);

        for (Matrix matrix : node.getMatrixArray()) {
            result.addTransform(new MatrixTransform(matrix));
        }

        for (Node child : node.getNodeArray()) {
            NodeResult childResult = parse(child, library);
            result.addChild(childResult);
        }

        for (InstanceWithExtra child : node.getInstanceNodeArray()) {
            NodeResult original = library.get(child.getUrl());
            result.addChildInstance(original, child.getName());
        }

        library.addToIds(result);

        return result;
    }
}
