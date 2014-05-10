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

package net.dryanhild.collada.schema14.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.AddressableType;
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
    private final List<Geometry> geometries = Lists.newArrayList();
    private final List<Material> materials = Lists.newArrayList();
    private final List<VisualScene> visualScenes = Lists.newArrayList();
    private final List<Effect> effects = Lists.newArrayList();

    private VisualScene mainScene;

    private VersionSupport version;

    public ColladaDocument14() {
    }

    @Override
    public Geometry getGeometry(String id) {
        return getElement(geometries, id);
    }

    @Override
    public Node getNode(String id) {
        return getElement(nodes, id);
    }

    @Override
    public Material getMaterial(String id) {
        return getElement(materials, id);
    }

    @Override
    public VisualScene getVisualScene(String id) {
        return getElement(visualScenes, id);
    }

    @Override
    public Effect getEffect(String id) {
        return getElement(effects, id);
    }

    private <T extends AddressableType> T getElement(Iterable<T> values, String id) {
        for (T element : values) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        throw new NoSuchElementIdException("Could not find element " + id);
    }

    @Override
    public Iterable<? extends Effect> getEffects() {
        return effects;
    }

    @Override
    public Iterable<? extends Material> getMaterials() {
        return materials;
    }

    @Override
    public Iterable<Geometry> getGeometries() {
        return geometries;
    }

    @Override
    public Iterable<Node> getNodes() {
        return nodes;
    }

    @Override
    public Iterable<VisualScene> getVisualScenes() {
        return visualScenes;
    }

    @Override
    public VisualScene getMainScene() {
        return mainScene;
    }

    public void setMainScene(VisualScene scene) {
        mainScene = scene;
    }

    public void addGeometry(Geometry geometry) {
        geometries.add(geometry);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addVisualScene(VisualScene scene) {
        visualScenes.add(scene);
    }

    public VersionSupport getVersion() {
        return version;
    }

    public void setVersion(VersionSupport version) {
        this.version = version;
    }
}
