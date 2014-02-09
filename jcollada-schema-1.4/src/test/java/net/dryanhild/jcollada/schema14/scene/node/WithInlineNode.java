package net.dryanhild.jcollada.schema14.scene.node;

import static org.assertj.core.api.Assertions.assertThat;
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
    private NodeLibrary library;

    @BeforeMethod
    public void resetNodes() {
        baseNode = Node.Factory.newInstance();
        baseNode.setId(BASE_NODE_ID);

        bottomNode = baseNode.addNewNode();
        bottomNode.setId(BOTTOM_NODE_ID);
        bottomNode.setName(BOTTOM_NODE_NAME);

        parser = new NodeParser();
        library = new NodeLibrary();
    }

    public void baseNodeHasOneChild() {
        NodeResult result = parser.parse(baseNode, library);

        assertThat(result.getChildren()).hasSize(1);
    }

    @Test(dependsOnMethods = "baseNodeHasOneChild")
    public void baseNodeHasBottomNodeChild() {
        NodeResult result = parser.parse(baseNode, library);

        NodeResult child = (NodeResult) result.getChildren().get(0);

        assertThat(child.getId()).isEqualTo(BOTTOM_NODE_ID);
        assertThat(child.getName()).isEqualTo(BOTTOM_NODE_NAME);
    }

}
