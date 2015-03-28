package net.dryanhild.collada.schema15.data;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.Material;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;
import net.dryanhild.collada.schema15.ColladaLoaderSchema15;

import java.util.Collections;

public class Collada15Document implements ColladaDocument {

    @Override
    public Iterable<Geometry> getGeometries() {
        return Collections.emptyList();
    }

    @Override
    public Geometry getGeometry(String id) {
        return null;
    }

    @Override
    public Iterable<Node> getNodes() {
        return Collections.emptyList();
    }

    @Override
    public Node getNode(String id) {
        return null;
    }

    @Override
    public Iterable<VisualScene> getVisualScenes() {
        return Collections.emptyList();
    }

    @Override
    public VisualScene getVisualScene(String id) {
        return null;
    }

    @Override
    public Iterable<? extends Effect> getEffects() {
        return Collections.emptyList();
    }

    @Override
    public Effect getEffect(String id) {
        return null;
    }

    @Override
    public Iterable<? extends Material> getMaterials() {
        return Collections.emptyList();
    }

    @Override
    public Material getMaterial(String id) {
        return null;
    }

    @Override
    public VisualScene getMainScene() {
        return null;
    }

    @Override
    public VersionSupport getVersion() {
        return ColladaLoaderSchema15.VERSION_1_5_0;
    }
}
