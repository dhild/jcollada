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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.data.Library;
import net.dryanhild.collada.schema14.scene.data.NodeResult;

import com.google.common.collect.ImmutableList;

public class NodeLibrary implements Library<NodeResult> {

    private final Map<URI, NodeResult> allNodes;
    private final List<NodeResult> topNodes;

    public NodeLibrary() {
        allNodes = new HashMap<>();
        topNodes = new ArrayList<>();
    }

    @Override
    public void add(NodeResult node) {
        addToIds(node);
        topNodes.add(node);
    }

    public void addToIds(NodeResult node) {
        if (node.getId() != null) {
            addNodeToIds(node);
        }
    }

    private void addNodeToIds(NodeResult node) {
        try {
            allNodes.put(new URI("#" + node.getId()), node);
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from the id " + node.getId(), e);
        }
    }

    @Override
    public List<NodeResult> getAll() {
        return ImmutableList.copyOf(topNodes);
    }

    @Override
    public NodeResult get(String id) {
        try {
            return get(new URI(id));
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from " + id, e);
        }
    }

    @Override
    public NodeResult get(URI uri) {
        NodeResult node = allNodes.get(uri);
        if (node == null) {
            throw new NoSuchElementIdException("Element with uri " + uri + " does not exist");
        }
        return node;
    }

}
