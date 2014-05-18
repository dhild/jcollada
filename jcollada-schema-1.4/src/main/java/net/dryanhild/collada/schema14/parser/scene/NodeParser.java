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

package net.dryanhild.collada.schema14.parser.scene;

import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.data.scene.NodeType;
import net.dryanhild.collada.schema14.data.scene.NodeImpl;
import net.dryanhild.collada.schema14.parser.AbstractParser;
import net.dryanhild.collada.schema14.parser.geometry.GeometryInstanceParser;
import net.dryanhild.collada.schema14.parser.transform.MatrixParser;
import net.dryanhild.collada.schema14.postprocessors.scene.InstanceNodePostprocessor;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

@Service
public class NodeParser extends AbstractParser<NodeImpl> {

    @Inject
    private GeometryInstanceParser geometryInstanceParser;

    @Inject
    private MatrixParser matrixParser;

    @Override
    public String getExpectedTag() {
        return "node";
    }

    @Override
    protected NodeImpl createObject() throws XmlPullParserException, IOException {
        return setAttributes(new NodeImpl());
    }

    @Override
    protected void handleAttribute(NodeImpl node, String attribute, String value) {
        switch (attribute) {
            case "type":
                node.setType(NodeType.valueOf(value));
                break;
            case "id":
                data.document.addNode(node);
                break;
        }
    }

    @Override
    protected void handleChildElement(NodeImpl parent, String childTag)
            throws IOException, XmlPullParserException {
        switch (childTag) {
            case "matrix":
                parent.addTransform(matrixParser.parse());
                break;
            case "instance_geometry":
                parent.addGeometry(geometryInstanceParser.parse());
                break;
            case "instance_node":
                addInstanceNodeProcessor(parent);
                break;
            case "node":
                parent.addChild(parse());
        }
    }

    private void addInstanceNodeProcessor(NodeImpl parent) {
        for (int i = 0; i < data.parser.getAttributeCount(); i++) {
            String key = data.parser.getAttributeName(i);
            if ("url".equals(key)) {
                String url = data.parser.getAttributeValue(i);
                data.postprocessors.add(new InstanceNodePostprocessor(url, parent));
                return;
            }
        }
        throw new IncorrectFormatException("Expected attribute \"url\" on element \"instance_node\"");
    }
}
