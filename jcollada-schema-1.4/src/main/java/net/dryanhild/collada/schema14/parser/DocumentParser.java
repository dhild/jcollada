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

package net.dryanhild.collada.schema14.parser;

import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.schema14.ColladaLoaderService14;
import net.dryanhild.collada.schema14.data.ColladaDocument14;
import net.dryanhild.collada.schema14.parser.fx.EffectsLibraryParser;
import net.dryanhild.collada.schema14.parser.geometry.GeometryLibraryParser;
import net.dryanhild.collada.schema14.parser.scene.NodeLibraryParser;
import net.dryanhild.collada.schema14.parser.scene.SceneParser;
import net.dryanhild.collada.schema14.parser.scene.VisualScenesLibraryParser;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

import static org.xmlpull.v1.XmlPullParser.START_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

@Service
public class DocumentParser extends AbstractParser<ColladaDocument14> {

    public static final String NAMESPACE = "http://www.collada.org/2005/11/COLLADASchema";

    @Inject
    private NodeLibraryParser nodeLibraryParser;

    @Inject
    private GeometryLibraryParser geometryLibraryParser;

    @Inject
    private EffectsLibraryParser effectsLibraryParser;

    @Inject
    private VisualScenesLibraryParser visualScenesLibraryParser;

    @Inject
    private SceneParser sceneParser;

    @Override
    public String getExpectedTag() {
        return "COLLADA";
    }

    @Override
    protected void validate() throws XmlPullParserException, IOException {
        data.parser.require(START_DOCUMENT, null, null);
        while (data.parser.getEventType() != START_TAG) {
            data.parser.next();
        }
        data.parser.require(START_TAG, NAMESPACE, getExpectedTag());
        data.document = new ColladaDocument14();
    }

    @Override
    protected ColladaDocument14 createObject() throws IOException, XmlPullParserException {
        return setAttributes(data.document);
    }

    @Override
    protected void handleAttribute(ColladaDocument14 object, String attribute, String value) {
        switch (attribute) {
            case "version":
                if ("1.4.0".equals(value)) {
                    object.setVersion(ColladaLoaderService14.VERSION_1_4_0);
                    break;
                }
                if ("1.4.1".equals(value)) {
                    object.setVersion(ColladaLoaderService14.VERSION_1_4_1);
                    break;
                }
                throw new IncorrectFormatException("Expected to get a 1.4.0 or 1.4.1 file!");
            case "base":
                // TODO: Process the base URI attribute.
        }
    }

    @Override
    protected void handleChildElement(ColladaDocument14 parent, String childTag)
            throws IOException, XmlPullParserException {
        switch (childTag) {
            case "library_nodes":
                nodeLibraryParser.parse();
                break;
            case "library_geometries":
                geometryLibraryParser.parse();
                break;
            case "library_effects":
                effectsLibraryParser.parse();
                break;
            case "library_visual_scenes":
                visualScenesLibraryParser.parse();
                break;
            case "scene":
                sceneParser.parse();
                break;
            default:
                break;
        }
    }
}
