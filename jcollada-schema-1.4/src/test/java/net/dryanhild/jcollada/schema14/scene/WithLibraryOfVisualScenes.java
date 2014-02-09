package net.dryanhild.jcollada.schema14.scene;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import net.dryanhild.jcollada.schema14.geometry.DefaultLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;
import net.dryanhild.jcollada.schema14.scene.data.VisualSceneResult;

import org.collada.x2005.x11.colladaSchema.InstanceWithExtra;
import org.collada.x2005.x11.colladaSchema.LibraryVisualScenesDocument.LibraryVisualScenes;
import org.collada.x2005.x11.colladaSchema.NodeDocument;
import org.collada.x2005.x11.colladaSchema.VisualSceneDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WithLibraryOfVisualScenes {

    private static final String VISUAL_SCENE_ID = "visual-scene-id";
    private static final String EMBEDDED_NODE_ID = "embedded-node-id";
    private static final String LIBRARY_NODE_ID = "library-node-id";

    private VisualSceneDocument.VisualScene visualScene;
    private NodeDocument.Node visualSceneNode;
    private NodeDocument.Node libraryNode;

    private LibraryVisualScenes library;
    private NodeLibrary nodeLibrary;
    private DefaultLibrary<GeometryResult> geometries;
    private DefaultLibrary<VisualSceneResult> visualScenes;
    private NodeParser nodeParser;

    @BeforeMethod
    public void resetLibrary() {
        libraryNode = NodeDocument.Node.Factory.newInstance();
        libraryNode.setId(LIBRARY_NODE_ID);

        nodeLibrary = new NodeLibrary();
        geometries = new DefaultLibrary<>();
        visualScenes = new DefaultLibrary<>();

        nodeParser = new NodeParser(nodeLibrary, geometries);
        nodeParser.parse(libraryNode);

        library = LibraryVisualScenes.Factory.newInstance();

        visualScene = library.addNewVisualScene();
        visualScene.setId(VISUAL_SCENE_ID);

        visualSceneNode = visualScene.addNewNode();
        visualSceneNode.setId(EMBEDDED_NODE_ID);
        InstanceWithExtra instance = visualSceneNode.addNewInstanceNode();
        instance.setUrl("#" + LIBRARY_NODE_ID);
    }

    public void visualSceneHasOneNode() {
        VisualSceneParser parser = new VisualSceneParser(nodeParser, visualScenes);

        parser.parse(visualScene);
        List<NodeResult> nodes = visualScenes.get("#" + VISUAL_SCENE_ID).getNodes();

        assertThat(nodes).hasSize(1);
    }

    @Test(dependsOnMethods = "visualSceneHasOneNode")
    public void visualSceneHasNodeWithCorrectId() {
        VisualSceneParser parser = new VisualSceneParser(nodeParser, visualScenes);

        parser.parse(visualScene);
        List<NodeResult> nodes = visualScenes.get("#" + VISUAL_SCENE_ID).getNodes();
        NodeResult node = nodes.get(0);

        assertThat(node.getId()).isEqualTo(EMBEDDED_NODE_ID);
    }

    @Test(dependsOnMethods = "visualSceneHasOneNode")
    public void visualSceneNodeHasReferencedById() {
        VisualSceneParser parser = new VisualSceneParser(nodeParser, visualScenes);

        parser.parse(visualScene);
        List<NodeResult> nodes = visualScenes.get("#" + VISUAL_SCENE_ID).getNodes();
        NodeResult node = nodes.get(0);

        assertThat(node.getChildren()).hasSize(1);
        assertThat(node.getChildren().get(0).getId()).isEqualTo(LIBRARY_NODE_ID);
    }

}
