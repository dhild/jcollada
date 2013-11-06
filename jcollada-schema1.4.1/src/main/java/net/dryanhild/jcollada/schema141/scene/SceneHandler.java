/**
 * 
 */
package net.dryanhild.jcollada.schema141.scene;

import javax.media.j3d.BranchGroup;

import net.dryanhild.jcollada.schema141.Handler;
import net.dryanhild.jcollada.schema141.gen.ColladaNode;
import net.dryanhild.jcollada.schema141.gen.InstanceWithExtra;
import net.dryanhild.jcollada.schema141.gen.VisualScene;
import net.dryanhild.jcollada.spi.ColladaContext;

/**
 * @author dhild
 * 
 */
public class SceneHandler implements Handler<BranchGroup> {

    private final InstanceWithExtra instance;

    public SceneHandler(InstanceWithExtra instance) {
        this.instance = instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BranchGroup process() {
        BranchGroup branch = new BranchGroup();

        VisualScene visualScene = ColladaContext.getElementById(instance.getUrl(), VisualScene.class);
        if (instance.getName() != null) {
            branch.setName(instance.getName());
        } else {
            branch.setName(visualScene.getName());
        }

        for (ColladaNode node : visualScene.getNodes()) {
            NodeHandler element = new NodeHandler(node.getId());
            branch.addChild(element.constructInstance());
        }

        return branch;
    }

}
