package net.dryanhild.collada.schema14;

import net.dryanhild.collada.data.Asset;
import net.dryanhild.collada.data.ColladaScene;
import net.dryanhild.collada.data.scene.VisualScene;
import net.dryanhild.collada.schema14.geometry.DefaultLibrary;
import net.dryanhild.collada.schema14.geometry.data.GeometryResult;
import net.dryanhild.collada.schema14.scene.NodeLibrary;
import net.dryanhild.collada.schema14.scene.data.VisualSceneResult;

public class ColladaScene14 implements ColladaScene {

    private final DefaultLibrary<GeometryResult> geometries;
    private final DefaultLibrary<VisualSceneResult> visualScenes;
    private final NodeLibrary nodes;

    private VisualScene mainScene;

    public ColladaScene14() {
        geometries = new DefaultLibrary<>();
        visualScenes = new DefaultLibrary<>();
        nodes = new NodeLibrary();
    }

    @Override
    public Asset getAsset() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DefaultLibrary<GeometryResult> getGeometries() {
        return geometries;
    }

    @Override
    public DefaultLibrary<VisualSceneResult> getVisualScenes() {
        return visualScenes;
    }

    @Override
    public NodeLibrary getNodes() {
        return nodes;
    }

    public void setMainScene(VisualScene scene) {
        mainScene = scene;
    }

    @Override
    public VisualScene getMainScene() {
        return mainScene;
    }

}
