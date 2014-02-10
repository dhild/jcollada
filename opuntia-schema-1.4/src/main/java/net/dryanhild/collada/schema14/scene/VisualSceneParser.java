package net.dryanhild.collada.schema14.scene;

import net.dryanhild.collada.schema14.geometry.DefaultLibrary;
import net.dryanhild.collada.schema14.scene.data.VisualSceneResult;

import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.collada.x2005.x11.colladaSchema.VisualSceneDocument.VisualScene;

public class VisualSceneParser {

    private final DefaultLibrary<VisualSceneResult> scenes;
    private final NodeParser nodeParser;

    public VisualSceneParser(NodeParser parser, DefaultLibrary<VisualSceneResult> scenes) {
        nodeParser = parser;
        this.scenes = scenes;
    }

    public void parse(VisualScene vs) {
        VisualSceneResult result = new VisualSceneResult(vs.getId());

        for (Node node : vs.getNodeArray()) {
            result.addNode(nodeParser.parse(node));
        }

        scenes.add(result);
    }

}
