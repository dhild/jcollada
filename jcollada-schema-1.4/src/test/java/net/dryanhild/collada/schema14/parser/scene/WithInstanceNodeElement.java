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

import net.dryanhild.collada.schema14.parser.BaseParserTest;
import net.dryanhild.collada.schema14.postprocessors.Postprocessor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WithInstanceNodeElement extends BaseParserTest {

    @Inject
    private NodeLibraryParser nodeParser;

    @Override
    protected String getDataString() {
        return "<library_nodes>" +
               "  <node id=\"mainNode\" name=\"nodeA\">" +
               "    <instance_node url=\"#otherNode\"/>" +
               "  </node>" +
               "  <node id=\"otherNode\" name=\"nodeB\"/>" +
               "</library_nodes>";
    }

    @BeforeMethod
    public void setNode() throws IOException, XmlPullParserException {
        nodeParser.parse();
        for (Postprocessor p : data.postprocessors) {
            p.process(data);
        }
    }

    @Test
    public void twoNodesExist() {
        assertThat(data.document.getNodes()).extracting("name").containsOnly("nodeA", "nodeB");
        assertThat(data.document.getNodes()).extracting("id").containsOnly("mainNode", "otherNode");
    }

    @Test
    public void otherNodeHasNoChildren() {
        assertThat(data.document.getNode("otherNode").getChildren()).isEmpty();
    }

    @Test
    public void nodeAHasNodeBChild() {
        assertThat(data.document.getNode("mainNode").getChildren()).extracting("name").containsOnly("nodeB");
        assertThat(data.document.getNode("mainNode").getChildren()).extracting("id").containsOnly("otherNode");
    }
}
