package net.dryanhild.jcollada.data;

import com.sun.j3d.loaders.Scene;

public interface ColladaScene extends Scene {

    AssetDescription getMainAsset();

}
