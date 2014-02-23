package net.dryanhild.collada.schema14;

import net.dryanhild.collada.schema14.geometry.DefaultLibrary;
import net.dryanhild.collada.schema14.geometry.GeometryParser;
import net.dryanhild.collada.schema14.geometry.data.GeometryResult;
import net.dryanhild.collada.schema14.scene.NodeLibrary;
import net.dryanhild.collada.schema14.scene.NodeParser;
import net.dryanhild.collada.schema14.scene.VisualSceneParser;
import net.dryanhild.collada.schema14.scene.data.VisualSceneResult;

import org.collada.x2005.x11.colladaSchema.COLLADADocument;
import org.collada.x2005.x11.colladaSchema.COLLADADocument.COLLADA;
import org.collada.x2005.x11.colladaSchema.COLLADADocument.COLLADA.Scene;
import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.InstanceWithExtra;
import org.collada.x2005.x11.colladaSchema.LibraryGeometriesDocument.LibraryGeometries;
import org.collada.x2005.x11.colladaSchema.LibraryNodesDocument.LibraryNodes;
import org.collada.x2005.x11.colladaSchema.LibraryVisualScenesDocument.LibraryVisualScenes;
import org.collada.x2005.x11.colladaSchema.NodeDocument;
import org.collada.x2005.x11.colladaSchema.VisualSceneDocument.VisualScene;

public class ParsingFactory {

    private final ColladaScene14 scene;
    private final GeometryParser geometryParser;
    private final NodeParser nodeParser;
    private final VisualSceneParser visualSceneParser;

    public ParsingFactory() {
        scene = new ColladaScene14();
        geometryParser = new GeometryParser(scene.getGeometries());
        nodeParser = new NodeParser(scene.getNodes(), scene.getGeometries());
        visualSceneParser = new VisualSceneParser(nodeParser, scene.getVisualScenes());
    }

    public ColladaScene14 getScene() {
        return scene;
    }

    public NodeLibrary getNodes() {
        return scene.getNodes();
    }

    public DefaultLibrary<GeometryResult> getGeometries() {
        return scene.getGeometries();
    }

    public DefaultLibrary<VisualSceneResult> getVisualScenes() {
        return scene.getVisualScenes();
    }

    public void parseDocument(COLLADADocument document) {
        COLLADA collada = document.getCOLLADA();

        for (LibraryGeometries lib : collada.getLibraryGeometriesArray()) {
            parseGeometries(lib);
        }

        for (LibraryNodes lib : collada.getLibraryNodesArray()) {
            parseNodes(lib);
        }

        for (LibraryVisualScenes lib : collada.getLibraryVisualScenesArray()) {
            parseVisualScenes(lib);
        }

        Scene mainScene = collada.getScene();
        if (mainScene != null) {
            InstanceWithExtra sceneInstance = mainScene.getInstanceVisualScene();
            if (sceneInstance != null) {
                VisualSceneResult mainVS = scene.getVisualScenes().get(sceneInstance.getUrl());
                scene.setMainScene(mainVS);
            }
        }
    }

    public void parseGeometries(LibraryGeometries library) {
        for (Geometry g : library.getGeometryArray()) {
            geometryParser.parse(g);
        }
    }

    public void parseVisualScenes(LibraryVisualScenes library) {
        for (VisualScene vs : library.getVisualSceneArray()) {
            visualSceneParser.parse(vs);
        }
    }

    public void parseNodes(LibraryNodes library) {
        for (NodeDocument.Node n : library.getNodeArray()) {
            nodeParser.parse(n);
        }
    }
}
