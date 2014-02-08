package net.dryanhild.jcollada.data;

import java.util.Collection;

import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;

public interface ColladaScene {

    Asset getAsset();

    Collection<Geometry> getGeometries();

    <T> T getElementById(String id, Class<T> type);

    Collection<Node> getNodes();

}
