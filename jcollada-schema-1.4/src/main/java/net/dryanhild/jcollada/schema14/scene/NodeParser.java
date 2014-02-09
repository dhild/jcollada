package net.dryanhild.jcollada.schema14.scene;

import net.dryanhild.jcollada.schema14.geometry.DefaultLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.scene.data.MatrixTransform;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.InstanceGeometryDocument.InstanceGeometry;
import org.collada.x2005.x11.colladaSchema.InstanceWithExtra;
import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;
import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.collada.x2005.x11.colladaSchema.NodeType;

public class NodeParser {

    private final DefaultLibrary<GeometryResult> geometries;
    private final NodeLibrary library;

    public NodeParser(NodeLibrary library, DefaultLibrary<GeometryResult> geometries) {
        this.library = library;
        this.geometries = geometries;
    }

    public NodeResult parse(Node node) {
        NodeResult result = parseImpl(node);

        library.add(result);

        return result;
    }

    private NodeResult parseImpl(Node node) {
        NodeType.Enum typeEnum = node.getType();
        String type = typeEnum.toString();
        NodeResult result = new NodeResult(node.getName(), node.getId(), type);

        for (Matrix matrix : node.getMatrixArray()) {
            result.addTransform(new MatrixTransform(matrix));
        }

        for (Node child : node.getNodeArray()) {
            NodeResult childResult = parseImpl(child);
            result.addChild(childResult);
        }

        for (InstanceWithExtra child : node.getInstanceNodeArray()) {
            NodeResult original = library.get(child.getUrl());
            result.addChildInstance(original, child.getName());
        }

        for (InstanceGeometry instance : node.getInstanceGeometryArray()) {
            GeometryResult geometry = geometries.get(instance.getUrl());
            result.addGeometry(geometry);
        }

        library.addToIds(result);

        return result;
    }
}
