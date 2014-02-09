package net.dryanhild.jcollada.schema14.scene.node;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.schema14.geometry.DefaultLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.scene.NodeLibrary;
import net.dryanhild.jcollada.schema14.scene.NodeParser;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WithInlineNode {

    private static final String BOTTOM_NODE_ID = "bottomNode";
    private static final String BOTTOM_NODE_NAME = "bottomNodeName";
    private static final String BASE_NODE_ID = "node-with-sub-node";
    private Node bottomNode;
    private Node baseNode;
    private NodeParser parser;
    private DefaultLibrary<GeometryResult> geometries;
    private NodeLibrary library;

    @BeforeMethod
    public void resetNodes() {
        baseNode = Node.Factory.newInstance();
        baseNode.setId(BASE_NODE_ID);

        bottomNode = baseNode.addNewNode();
        bottomNode.setId(BOTTOM_NODE_ID);
        bottomNode.setName(BOTTOM_NODE_NAME);

        library = new NodeLibrary();
        geometries = new DefaultLibrary<>();
        parser = new NodeParser(library, geometries);
    }

    public void baseNodeHasOneChild() {
        parser.parse(baseNode);

        assertThat(library.get("#" + BASE_NODE_ID).getChildren()).hasSize(1);
    }

    @Test(dependsOnMethods = "baseNodeHasOneChild")
    public void baseNodeHasBottomNodeChild() {
        parser.parse(baseNode);

        NodeResult base = library.get("#" + BASE_NODE_ID);
        NodeResult child = (NodeResult) base.getChildren().get(0);

        assertThat(child.getId()).isEqualTo(BOTTOM_NODE_ID);
        assertThat(child.getName()).isEqualTo(BOTTOM_NODE_NAME);
    }

}
