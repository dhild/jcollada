/**
 * 
 */
package net.dryanhild.jcollada.schema141.handlers.impl;

import net.dryanhild.jcollada.schema141.handlers.ElementHandler;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.SceneBase;

/**
 * @author dhild
 * 
 */
public class COLLADAHandler implements ElementHandler<Scene> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene process() {
        SceneBase scene = new SceneBase();

        return scene;
    }

}
