package net.dryanhild.jcollada.data;

import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;

public interface ColladaScene {

    Asset getAsset();

    Library<Geometry> getGeometries();

    Library<Node> getNodes();

}
