package net.dryanhild.collada.schema14;

import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.Material;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

public class ColladaDocument14 implements ColladaDocument {

    private VisualScene mainScene;

    public ColladaDocument14() {
    }

    @Override
    public Geometry getGeometry(String id) {
        return null;
    }

    @Override
    public Node getNode(String id) {
        return null;
    }

    @Override
    public VisualScene getVisualScene(String id) {
        return null;
    }

    @Override
    public Iterable<? extends Effect> getEffects() {
        return null;
    }

    @Override
    public Effect getEffect(String id) {
        return null;
    }

    @Override
    public Iterable<? extends Material> getMaterials() {
        return null;
    }

    @Override
    public Material getMaterial(String id) {
        return null;
    }

    @Override
    public Iterable<Geometry> getGeometries() {
        return null;
    }

    @Override
    public Iterable<Node> getNodes() {
        return null;
    }

    @Override
    public Iterable<VisualScene> getVisualScenes() {
        return null;
    }

    public void setMainScene(VisualScene scene) {
        mainScene = scene;
    }

    @Override
    public VisualScene getMainScene() {
        return mainScene;
    }

}
