/*
 * Copyright (c) 2014 D. Ryan Hild <d.ryan.hild@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.dryanhild.collada.schema14.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.Material;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

import java.util.List;
import java.util.Set;

public class ColladaDocument14 implements ColladaDocument {

    private final Set<String> documentLocations = Sets.newHashSet();

    private final List<Node> nodes = Lists.newArrayList();

    private VisualScene mainScene;

    public ColladaDocument14() {
    }

    @Override
    public Geometry getGeometry(String id) {
        return null;
    }

    @Override
    public Node getNode(String id) {
        for (Node node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        throw new NoSuchElementIdException("Could not find node " + id);
    }

    @Override
    public VisualScene getVisualScene(String id) {
        return null;
    }

    @Override
    public Iterable<? extends Effect> getEffects() {
        return null;
    }

    @Override
    public Effect getEffect(String id) {
        return null;
    }

    @Override
    public Iterable<? extends Material> getMaterials() {
        return null;
    }

    @Override
    public Material getMaterial(String id) {
        return null;
    }

    @Override
    public Iterable<Geometry> getGeometries() {
        return null;
    }

    @Override
    public Iterable<Node> getNodes() {
        return nodes;
    }

    @Override
    public Iterable<VisualScene> getVisualScenes() {
        return null;
    }

    public void setMainScene(VisualScene scene) {
        mainScene = scene;
    }

    @Override
    public VisualScene getMainScene() {
        return mainScene;
    }


    public void addNode(Node node) {
        nodes.add(node);
    }

}
