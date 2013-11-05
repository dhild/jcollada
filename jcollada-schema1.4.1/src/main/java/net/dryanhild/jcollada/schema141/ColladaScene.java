/*
 * Copyright (c) 2013, D. Ryan Hild <d.ryan.hild@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.dryanhild.jcollada.schema141;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;

import net.dryanhild.jcollada.schema141.gen.COLLADA;
import net.dryanhild.jcollada.schema141.gen.Geometry;
import net.dryanhild.jcollada.schema141.gen.InstanceWithExtra;
import net.dryanhild.jcollada.schema141.gen.LibraryGeometries;
import net.dryanhild.jcollada.schema141.gen.LibraryNodes;
import net.dryanhild.jcollada.schema141.gen.LibraryVisualScenes;
import net.dryanhild.jcollada.schema141.gen.Node;
import net.dryanhild.jcollada.schema141.gen.VisualScene;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.SceneBase;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class ColladaScene {

    final Logger logger = LogManager.getLogger(ColladaScene.class);

    COLLADA.Scene mainScene;

    /** Adds the collada file to the data to be processed. */
    void load(COLLADA collada) {
        for (Object obj : collada.getLibraryAnimationsAndLibraryAnimationClipsAndLibraryCameras()) {
            if (obj instanceof LibraryGeometries) {
                LibraryGeometries library = (LibraryGeometries) obj;
                for (Geometry g : library.getGeometries()) {
                    GeometryUtil.createGeometry(g, context);
                }
            } else if (obj instanceof LibraryNodes) {
                LibraryNodes library = (LibraryNodes) obj;
                for (Node n : library.getNodes()) {
                    NodeElement.createNode(n, context);
                }
            } else if (obj instanceof LibraryVisualScenes) {
                LibraryVisualScenes library = (LibraryVisualScenes) obj;
                for (VisualScene v : library.getVisualScenes()) {
                    addVisualScene(v);
                }
            } else {
                logger.debug("Skipping library of type {}", obj.getClass().getName());
            }
        }
        if (collada.getScene() != null) {
            mainScene = collada.getScene();
        }
    }

    /** Adds the VisualScene and all contained Nodes to their Maps. */
    private void addVisualScene(VisualScene scene) {
        context.addObjectById(scene.getId(), scene);
        for (Node n : scene.getNodes()) {
            NodeElement.createNode(n, context);
        }
    }

    /**
     * Process all of the COLLADA files that have been loaded. This method
     * assumes that all of the files needing to be referenced have been.
     */
    public SceneBase constructScene() {
        if (mainScene == null) {
            throw new ParsingErrorException("Couldn't find the <scene> element!");
        }

        SceneBase scene = new SceneBase();

        scene.setSceneGroup(makeVisualScene(mainScene.getInstanceVisualScene()));

        return scene;
    }

    private VisualScene getVisualSceneType(String url) {
        return context.getObjectById(url, VisualScene.class);
    }

    private javax.media.j3d.Node getNode(String url) {
        return context.getObjectById(url, NodeElement.class).getInstance();
    }

    private Shape3D getGeometryInstance(String url) {
        return context.getObjectById(url, GeometryUtil.class).getShape3D();
    }

    private BranchGroup makeVisualScene(InstanceWithExtra instance) {
        BranchGroup branch = new BranchGroup();

        VisualScene visualScene = getVisualSceneType(instance.getUrl());
        if (instance.getName() != null) {
            branch.setName(instance.getName());
        } else {
            branch.setName(visualScene.getName());
        }

        for (Node node : visualScene.getNodes()) {
            NodeElement element = context.getObjectById(node.getId(), NodeElement.class);
            branch.addChild(element.getInstance());
        }

        return branch;
    }

}
