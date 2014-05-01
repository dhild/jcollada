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

package net.dryanhild.collada.schema14.scene.data;

import java.util.ArrayList;
import java.util.List;

import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.NodeType;
import net.dryanhild.collada.data.transform.Transform;

public class NodeResult implements Node {

    private final String name;
    private final String id;
    private final NodeType type;

    private final List<Node> children;
    private final List<Geometry> geometries;
    private final List<Transform> transforms;

    public NodeResult(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = NodeType.valueOf(type);

        children = new ArrayList<>();
        geometries = new ArrayList<>();
        transforms = new ArrayList<>();
    }

    protected NodeResult(String name, NodeResult copy) {
        this.name = name;
        this.id = copy.id;
        this.type = copy.type;

        children = new ArrayList<>(copy.children);
        geometries = new ArrayList<>(copy.geometries);
        transforms = new ArrayList<>(copy.transforms);
    }

    public void addChild(NodeResult child) {
        children.add(child);
    }

    public void addChildInstance(NodeResult child, String childName) {
        children.add(new NodeResult(childName, child));
    }

    public void addGeometry(Geometry geometry) {
        geometries.add(geometry);
    }

    public void addTransform(Transform transform) {
        transforms.add(transform);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public List<Geometry> getGeometries() {
        return geometries;
    }

    @Override
    public List<Transform> getTransforms() {
        return transforms;
    }

}
