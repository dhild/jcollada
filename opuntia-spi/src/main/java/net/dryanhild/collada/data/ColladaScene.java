package net.dryanhild.collada.data;

import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

public interface ColladaScene {

    Asset getAsset();

    Library<? extends Geometry> getGeometries();

    Library<? extends Node> getNodes();

    Library<? extends VisualScene> getVisualScenes();

    VisualScene getMainScene();

}
