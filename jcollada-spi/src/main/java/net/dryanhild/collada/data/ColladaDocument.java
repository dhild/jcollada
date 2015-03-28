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

package net.dryanhild.collada.data;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.Material;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

public interface ColladaDocument {

    Iterable<Geometry> getGeometries();

    /**
     * Accessor method for geometries by ID.
     *
     * @param id The ID of the geometry (not including the '#' character for URI references.)
     * @return The geometry with the given ID, if it is present.
     * @throws java.util.NoSuchElementException If the given geometry does not exist.
     */
    Geometry getGeometry(String id);

    Iterable<Node> getNodes();

    /**
     * Accessor method for nodes by ID.
     *
     * @param id The ID of the node (not including the '#' character for URI references.)
     * @return The node with the given ID, if it is present.
     * @throws java.util.NoSuchElementException If the given node does not exist.
     */
    Node getNode(String id);

    Iterable<VisualScene> getVisualScenes();

    VisualScene getVisualScene(String id);

    Iterable<? extends Effect> getEffects();

    Effect getEffect(String id);

    Iterable<? extends Material> getMaterials();

    Material getMaterial(String id);

    VisualScene getMainScene();

    VersionSupport getVersion();

}
