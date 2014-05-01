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

package net.dryanhild.collada.schema14.scene;

import net.dryanhild.collada.schema14.geometry.DefaultLibrary;
import net.dryanhild.collada.schema14.geometry.data.GeometryResult;
import net.dryanhild.collada.schema14.scene.data.MatrixTransform;
import net.dryanhild.collada.schema14.scene.data.NodeResult;

import org.collada.x2005.x11.colladaSchema.InstanceGeometryDocument.InstanceGeometry;
import org.collada.x2005.x11.colladaSchema.InstanceWithExtra;
import org.collada.x2005.x11.colladaSchema.MatrixDocument.Matrix;
import org.collada.x2005.x11.colladaSchema.NodeDocument.Node;
import org.collada.x2005.x11.colladaSchema.NodeType;

public class NodeParser {

    private final DefaultLibrary<GeometryResult> geometries;
    private final NodeLibrary library;

    public NodeParser(NodeLibrary library, DefaultLibrary<GeometryResult> geometries) {
        this.library = library;
        this.geometries = geometries;
    }

    public NodeResult parse(Node node) {
        NodeResult result = parseImpl(node);

        library.add(result);

        return result;
    }

    private NodeResult parseImpl(Node node) {
        NodeType.Enum typeEnum = node.getType();
        String type = typeEnum.toString();
        NodeResult result = new NodeResult(node.getName(), node.getId(), type);

        for (Matrix matrix : node.getMatrixArray()) {
            result.addTransform(new MatrixTransform(matrix));
        }

        for (Node child : node.getNodeArray()) {
            NodeResult childResult = parseImpl(child);
            result.addChild(childResult);
        }

        for (InstanceWithExtra child : node.getInstanceNodeArray()) {
            NodeResult original = library.get(child.getUrl());
            result.addChildInstance(original, child.getName());
        }

        for (InstanceGeometry instance : node.getInstanceGeometryArray()) {
            GeometryResult geometry = geometries.get(instance.getUrl());
            result.addGeometry(geometry);
        }

        library.addToIds(result);

        return result;
    }
}
