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

package net.dryanhild.collada.schema14.parser.geometry.mesh;

import net.dryanhild.collada.schema14.data.geometry.TrianglesHolder;
import net.dryanhild.collada.schema14.parser.AbstractParser;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

@Service
public class TrianglesParser extends AbstractParser<TrianglesHolder> {

    @Override
    public String getExpectedTag() {
        return "triangles";
    }

    @Override
    protected TrianglesHolder createObject() throws XmlPullParserException {
        return new TrianglesHolder();
    }

    @Override
    protected void handleChildElement(TrianglesHolder parent, String childTag) throws IOException, XmlPullParserException {
        switch (childTag) {
            case "input":
                addInput(parent);
                break;
            case "p":
                parent.setP(readInts());
        }
    }

    private void addInput(TrianglesHolder parent) throws IOException, XmlPullParserException {
        String semantic = null;
        String source = null;
        int offset = 0;
        for (int i = 0; i < data.parser.getAttributeCount(); i++) {
            String value = data.parser.getAttributeValue(i);
            switch (data.parser.getAttributeName(i)) {
                case "semantic":
                    semantic = value;
                    break;
                case "source":
                    source = value;
                    break;
                case "offset":
                    offset = Integer.valueOf(value);
            }
        }
        parent.addInput(semantic, source, offset);
    }
}
