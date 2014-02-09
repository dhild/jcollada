package net.dryanhild.jcollada.schema14.scene.node;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.dryanhild.jcollada.data.scene.NodeType;
import net.dryanhild.jcollada.data.transform.Transform;
import net.dryanhild.jcollada.schema14.scene.NodeParser;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;
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

        assertThat(result.getName()).isEqualTo(BOTTOM_NODE_NAME);
    }

    public void bottomNodeHasId() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result.getId()).isEqualTo(BOTTOM_NODE_ID);
    }

    public void bottomNodeHasTypeNode() {
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result.getType()).isEqualTo(NodeType.NODE);
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

    private void addSimpleMatrix() {
        Matrix matrix = bottomNode.addNewMatrix();
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            values.add(Double.valueOf(i));
        }
        matrix.setListValue(values);
    }

    public void withMatrixHasMatrixTransform() {
        addSimpleMatrix();
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        assertThat(result.getTransforms()).hasSize(1);
    }

    @Test(dependsOnMethods = "withMatrixHasMatrixTransform")
    public void withMatrixHasCorrectMatrixValues() {
        addSimpleMatrix();
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        Transform trans = result.getTransforms().get(0);

        assertThat(trans.toMatrix().m00).isEqualTo(0);
        assertThat(trans.toMatrix().m01).isEqualTo(1);
        assertThat(trans.toMatrix().m02).isEqualTo(2);
        assertThat(trans.toMatrix().m03).isEqualTo(3);
        assertThat(trans.toMatrix().m10).isEqualTo(4);
        assertThat(trans.toMatrix().m11).isEqualTo(5);
        assertThat(trans.toMatrix().m12).isEqualTo(6);
        assertThat(trans.toMatrix().m13).isEqualTo(7);
        assertThat(trans.toMatrix().m20).isEqualTo(8);
        assertThat(trans.toMatrix().m21).isEqualTo(9);
        assertThat(trans.toMatrix().m22).isEqualTo(10);
        assertThat(trans.toMatrix().m23).isEqualTo(11);
        assertThat(trans.toMatrix().m30).isEqualTo(12);
        assertThat(trans.toMatrix().m31).isEqualTo(13);
        assertThat(trans.toMatrix().m32).isEqualTo(14);
        assertThat(trans.toMatrix().m33).isEqualTo(15);
    }

    @Test(dependsOnMethods = "withMatrixHasMatrixTransform")
    public void withMatrixHasCorrectFloatBufferValues() {
        addSimpleMatrix();
        NodeParser parser = new NodeParser(bottomNode);

        NodeResult result = parser.parse();

        Transform trans = result.getTransforms().get(0);

        FloatBuffer buffer = FloatBuffer.allocate(16);

        trans.putAsColumnMatrix(buffer);

        assertThat(buffer.array()).containsExactly(0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15);
    }
}
