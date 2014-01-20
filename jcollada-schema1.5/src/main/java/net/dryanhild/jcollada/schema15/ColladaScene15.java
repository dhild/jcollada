package net.dryanhild.jcollada.schema15;

import net.dryanhild.jcollada.data.AssetDescription;
import net.dryanhild.jcollada.data.ColladaScene;

import com.sun.j3d.loaders.SceneBase;

class ColladaScene15 extends SceneBase implements ColladaScene {

    private AssetDescription mainAsset;

    public void setMainAsset(AssetDescription asset) {
        mainAsset = asset;
    }

    @Override
    public AssetDescription getMainAsset() {
        return mainAsset;
    }

}
