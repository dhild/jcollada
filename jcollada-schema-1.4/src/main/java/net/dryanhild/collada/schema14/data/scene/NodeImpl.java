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

package net.dryanhild.collada.schema14.data.scene;

import com.google.common.collect.Lists;
import net.dryanhild.collada.data.geometry.GeometryInstance;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.NodeType;
import net.dryanhild.collada.data.transform.Transform;
import net.dryanhild.collada.schema14.data.AbstractNameableAddressableType;

import java.util.List;

public class NodeImpl extends AbstractNameableAddressableType implements Node {

    private NodeType type;
    private final List<Node> children = Lists.newArrayList();
    private final List<GeometryInstance> geometries = Lists.newArrayList();
    private final List<Transform> transforms = Lists.newArrayList();


    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public List<GeometryInstance> getGeometries() {
        return geometries;
    }

    @Override
    public List<Transform> getTransforms() {
        return transforms;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void addGeometry(GeometryInstance geometry) {
        geometries.add(geometry);
    }

    public void addTransform(Transform transform) {
        transforms.add(transform);
    }

}
