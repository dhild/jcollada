package net.dryanhild.jcollada.schema14.scene.node;

import static net.dryanhild.jcollada.schema14.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.data.scene.NodeType;
import net.dryanhild.jcollada.schema14.scene.NodeParser;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WithSingleNode {

    private static final String BOTTOM_NODE_ID = "bottomNode";
    private static final String BOTTOM_NODE_NAME = "bottomNodeName";
    private static final String NODE_WITH_INSTANCE_ID = "topNode";
    private Node bottomNode;
    private Node nodeWithInstanceBottomNode;

    @BeforeMethod
    public void resetNodes() {
        bottomNode = Node.Factory.newInstance();
        bottomNode.setId(BOTTOM_NODE_ID);
        bottomNode.setName(BOTTOM_NODE_NAME);

        nodeWithInstanceBottomNode = Node.Factory.newInstance();
        nodeWithInstanceBottomNode.setId(NODE_WITH_INSTANCE_ID);
    }

    public void bottomNodeHasName() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result).hasName(BOTTOM_NODE_NAME);
    }

    public void bottomNodeHasId() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result).hasId(BOTTOM_NODE_ID);
    }

    public void bottomNodeHasTypeNode() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result).hasType(NodeType.NODE);
    }

    public void bottomNodeHasNoChildren() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result.getChildren()).isEmpty();
    }

    public void bottomNodeHasNoGeometries() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result.getGeometries()).isEmpty();
    }
}
