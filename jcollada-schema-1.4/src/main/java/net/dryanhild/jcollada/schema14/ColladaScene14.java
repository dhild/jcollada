package net.dryanhild.jcollada.schema14;

import net.dryanhild.jcollada.data.Asset;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.Library;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;
import net.dryanhild.jcollada.schema14.geometry.ElementLibrary;

public class ColladaScene14 implements ColladaScene {

    private final ElementLibrary<Geometry> geometries;
    private final ElementLibrary<Node> nodes;

    public ColladaScene14() {
        geometries = new ElementLibrary<>();
        nodes = new ElementLibrary<>();
    }

    @Override
    public Asset getAsset() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Library<Geometry> getGeometries() {
        return geometries;
    }

    @Override
    public Library<Node> getNodes() {
        return nodes;
    }

}
