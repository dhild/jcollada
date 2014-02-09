package net.dryanhild.jcollada.schema14;

import net.dryanhild.jcollada.data.Asset;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.data.Library;
import net.dryanhild.jcollada.schema14.geometry.ElementLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;
import net.dryanhild.jcollada.schema14.scene.NodeLibrary;

public class ColladaScene14 implements ColladaScene {

    private final ElementLibrary<GeometryResult> geometries;
    private final NodeLibrary nodes;

    public ColladaScene14() {
        geometries = new ElementLibrary<>();
        nodes = new NodeLibrary();
    }

    @Override
    public Asset getAsset() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Library<GeometryResult> getGeometries() {
        return geometries;
    }

    @Override
    public NodeLibrary getNodes() {
        return nodes;
    }

}
