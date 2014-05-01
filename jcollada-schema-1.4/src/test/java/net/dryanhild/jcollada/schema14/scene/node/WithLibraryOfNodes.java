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

package net.dryanhild.jcollada.schema14.scene.node;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.schema14.ParsingFactory;

import org.collada.x2005.x11.colladaSchema.InstanceWithExtra;
import org.collada.x2005.x11.colladaSchema.LibraryNodesDocument.LibraryNodes;
import org.collada.x2005.x11.colladaSchema.NodeDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WithLibraryOfNodes {

    private static final String SUB_NODE_ID = "bottomNode";
    private static final String SUB_NODE_NAME = "bottomNodeName";
    private static final String NODE_WITH_SUB_NODE_ID = "node-with-sub-node";
    private static final String NODE_WITH_SUB_NODE_NAME = "node-with-sub-node-name";
    private static final String NODE_WITH_INSTANCE_NODE_ID = "node-with-instance-node";
    private static final String INSTANCE_NODE_NAME = "instance-node";
    private static final String INSTANCE_SUB_NODE_NAME = "instance-sub-node";

    private NodeDocument.Node subNode;
    private NodeDocument.Node nodeWithSubNode;
    private NodeDocument.Node nodeWithInstanceNode;

    private LibraryNodes library;
    private ParsingFactory parsingFactory;

    @BeforeMethod
    public void resetDocument() {
        library = LibraryNodes.Factory.newInstance();
        nodeWithSubNode = library.addNewNode();
        nodeWithSubNode.setId(NODE_WITH_SUB_NODE_ID);
        nodeWithSubNode.setName(NODE_WITH_SUB_NODE_NAME);

        subNode = nodeWithSubNode.addNewNode();
        subNode.setId(SUB_NODE_ID);
        subNode.setName(SUB_NODE_NAME);

        nodeWithInstanceNode = library.addNewNode();
        nodeWithInstanceNode.setId(NODE_WITH_INSTANCE_NODE_ID);
        nodeWithInstanceNode.setName(INSTANCE_NODE_NAME);
        InstanceWithExtra instance = nodeWithInstanceNode.addNewInstanceNode();
        instance.setUrl("#" + SUB_NODE_ID);
        instance.setName(INSTANCE_SUB_NODE_NAME);

        parsingFactory = new ParsingFactory();
    }

    public void libraryHasTwoBaseNodes() {
        parsingFactory.parseNodes(library);

        assertThat(parsingFactory.getNodes().getAll()).hasSize(2);
    }

    public void instanceNodeAccessibleById() {
        parsingFactory.parseNodes(library);

        Node node = parsingFactory.getNodes().get("#" + NODE_WITH_INSTANCE_NODE_ID);

        assertThat(node.getId()).isEqualTo(NODE_WITH_INSTANCE_NODE_ID);
        assertThat(node.getName()).isEqualTo(INSTANCE_NODE_NAME);
    }

    public void subNodeAccessibleById() {
        parsingFactory.parseNodes(library);

        Node node = parsingFactory.getNodes().get("#" + NODE_WITH_SUB_NODE_ID);

        assertThat(node.getId()).isEqualTo(NODE_WITH_SUB_NODE_ID);
        assertThat(node.getName()).isEqualTo(NODE_WITH_SUB_NODE_NAME);
    }

    public void leafNodeAccessibleById() {
        parsingFactory.parseNodes(library);

        Node node = parsingFactory.getNodes().get("#" + SUB_NODE_ID);

        assertThat(node.getId()).isEqualTo(SUB_NODE_ID);
        assertThat(node.getName()).isEqualTo(SUB_NODE_NAME);
    }

    public void instanceNodeHasOneChild() {
        parsingFactory.parseNodes(library);

        Node node = parsingFactory.getNodes().get("#" + NODE_WITH_INSTANCE_NODE_ID);
        assertThat(node.getChildren()).hasSize(1);
    }

    @Test(dependsOnMethods = "instanceNodeHasOneChild")
    public void instanceNodeHasChildWithCorrectName() {
        parsingFactory.parseNodes(library);

        Node node = parsingFactory.getNodes().get("#" + NODE_WITH_INSTANCE_NODE_ID);
        Node child = node.getChildren().get(0);

        assertThat(child.getName()).isEqualTo(INSTANCE_SUB_NODE_NAME);
    }
}
