/**
 * 
 */
package net.dryanhild.jcollada.schema141;

import net.dryanhild.jcollada.metadata.Asset;
import net.dryanhild.jcollada.schema141.gen.ColladaAsset;
import net.dryanhild.jcollada.schema141.metadata.AssetHandler;
import net.dryanhild.jcollada.spi.ColladaContext;

import org.w3c.dom.Document;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.SceneBase;

/**
 * Parses the current COLLADA context into a Scene object.
 * 
 * This process may involve handling several distinct COLLADA files and
 * resources.
 * 
 * @author dhild
 * 
 */
public class COLLADAContextHandler implements Handler<Scene> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene process() {
        SceneBase scene = new SceneBase();

        Document mainDocument = ColladaContext.getMainDocument();

        ColladaAsset colladaAsset = ColladaContext.getElementByXPath(mainDocument, "/COLLADA/asset", ColladaAsset.class);
        Asset asset = new AssetHandler(colladaAsset).process();

        return scene;
    }
}
