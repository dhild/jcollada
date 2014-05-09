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

package net.dryanhild.collada.schema14.parser.geometry;

import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.schema14.ParsingData;
import net.dryanhild.collada.schema14.data.geometry.MeshImpl;
import net.dryanhild.collada.schema14.parser.XmlParser;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

@Service
public class GeometryParser implements XmlParser<Geometry> {

    @Inject
    private ParsingData data;

    @Inject
    private MeshParser meshParser;

    @Override
    public Geometry parse() throws XmlPullParserException, IOException {
        data.parser.require(XmlPullParser.START_TAG, null, "geometry");

        String name = null;
        String id = null;

        for (int i = 0; i < data.parser.getAttributeCount(); i++) {
            switch (data.parser.getAttributeName(i)) {
                case "name":
                    name = data.parser.getAttributeValue(i);
                    break;
                case "id":
                    id = data.parser.getAttributeValue(i);
                    break;
            }
        }

        final int depth = data.parser.getDepth();
        Geometry geometry = null;

        while (data.parser.getDepth() >= depth) {
            if (data.parser.next() == XmlPullParser.START_TAG) {
                if (data.parser.getName().equals(meshParser.getExpectedTag())) {
                    MeshImpl mesh = meshParser.parse();
                    mesh.setId(id);
                    mesh.setName(name);
                    geometry = mesh;
                } else {
                    final int innerDepth = data.parser.getDepth();
                    while (data.parser.getDepth() >= innerDepth) {
                        data.parser.next();
                    }
                }
            }
        }

        return geometry;
    }
}
