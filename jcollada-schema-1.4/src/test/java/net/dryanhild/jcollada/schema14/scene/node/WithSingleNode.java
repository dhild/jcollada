package net.dryanhild.jcollada.schema14.scene.node;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.dryanhild.jcollada.data.scene.NodeType;
import net.dryanhild.jcollada.data.transform.Transform;
import net.dryanhild.jcollada.schema14.geometry.DefaultLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.scene.NodeLibrary;
import net.dryanhild.jcollada.schema14.scene.NodeParser;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;
import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WithSingleNode {

    private static final String NODE_ID = "bottomNode";
    private static final String NODE_NAME = "bottomNodeName";
    private Node node;
    private NodeParser parser;
    private DefaultLibrary<GeometryResult> geometries;
    private NodeLibrary library;

    @BeforeMethod
    public void resetNodes() {
        node = Node.Factory.newInstance();
        node.setId(NODE_ID);
        node.setName(NODE_NAME);

        library = new NodeLibrary();
        geometries = new DefaultLibrary<>();
        parser = new NodeParser(library, geometries);
    }

    public void nodeHasName() {
        NodeResult result = parser.parse(node);

        assertThat(result.getName()).isEqualTo(NODE_NAME);
    }

    public void nodeHasId() {
        NodeResult result = parser.parse(node);

        assertThat(result.getId()).isEqualTo(NODE_ID);
    }

    public void nodeHasTypeNode() {
        NodeResult result = parser.parse(node);

        assertThat(result.getType()).isEqualTo(NodeType.NODE);
    }

    public void nodeHasNoChildren() {
        NodeResult result = parser.parse(node);

        assertThat(result.getChildren()).isEmpty();
    }

    public void nodeHasNoGeometries() {
        NodeResult result = parser.parse(node);

        assertThat(result.getGeometries()).isEmpty();
    }

    private void addSimpleMatrix() {
        Matrix matrix = node.addNewMatrix();
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            values.add(Double.valueOf(i));
        }
        matrix.setListValue(values);
    }

    public void withMatrixHasMatrixTransform() {
        addSimpleMatrix();

        NodeResult result = parser.parse(node);

        assertThat(result.getTransforms()).hasSize(1);
    }

    @Test(dependsOnMethods = "withMatrixHasMatrixTransform")
    public void withMatrixHasCorrectMatrixValues() {
        addSimpleMatrix();

        NodeResult result = parser.parse(node);

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

        NodeResult result = parser.parse(node);

        Transform trans = result.getTransforms().get(0);

        FloatBuffer buffer = FloatBuffer.allocate(16);

        trans.putAsColumnMatrix(buffer);

        assertThat(buffer.array()).containsExactly(0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15);
    }
}
