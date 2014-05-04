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

package net.dryanhild.collada.schema14.parser.geometry.source;

import com.google.common.collect.Lists;
import net.dryanhild.collada.schema14.data.geometry.source.FloatAccessor;
import net.dryanhild.collada.schema14.data.geometry.source.FloatSource;
import net.dryanhild.collada.schema14.data.geometry.source.SourceAccessorParam;
import net.dryanhild.collada.schema14.parser.AbstractParser;
import org.glassfish.hk2.api.PerThread;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Service
@PerThread
public class SourceParser extends AbstractParser<FloatSource> {

    @Inject
    private FloatArrayParser floatArrayParser;

    protected FloatSource floatSource;

    protected TechniqueCommonParser techniqueCommonParser = new TechniqueCommonParser();
    protected AccessorParser accessorParser = new AccessorParser();
    protected ParamParser paramParser = new ParamParser();

    @Override
    public String getExpectedTag() {
        return "source";
    }

    @Override
    protected FloatSource createObject(XmlPullParser parser) throws XmlPullParserException, IOException {
        floatSource = setAttributes(parser, new FloatSource());
        return floatSource;
    }

    @Override
    protected FloatSource handleAttribute(FloatSource object, String attribute, String value) {
        switch (attribute) {
            case "id":
                object.setId(value);
                break;
            case "name":
                object.setName(value);
                break;
        }
        return object;
    }

    @Override
    protected void handleChildElement(XmlPullParser parser, FloatSource parent, String childTag)
            throws IOException, XmlPullParserException {
        switch (childTag) {
            case "float_array":
                floatSource.setSource(floatArrayParser.parse(parser));
                break;
            case "technique_common":
                techniqueCommonParser.parse(parser);
                break;
            default:
                skipElement(parser);
                break;
        }
    }

    private class TechniqueCommonParser extends AbstractParser<FloatSource> {
        @Override
        public String getExpectedTag() {
            return "technique_common";
        }

        @Override
        protected FloatSource createObject(XmlPullParser parser) throws XmlPullParserException, IOException {
            return floatSource;
        }

        @Override
        protected void handleChildElement(XmlPullParser parser, FloatSource parent, String childTag)
                throws IOException, XmlPullParserException {
            switch (childTag) {
                case "accessor":
                    floatSource.setCommonAccessor(accessorParser.parse(parser));
                    break;
                default:
                    skipElement(parser);
                    break;
            }
        }
    }

    private class AccessorParser extends AbstractParser<FloatAccessor> {
        @Override
        public String getExpectedTag() {
            return "accessor";
        }

        @Override
        protected FloatAccessor createObject(XmlPullParser parser) throws XmlPullParserException, IOException {
            FloatAccessor source = setAttributes(parser, new FloatAccessor());
            parser.next();

            List<SourceAccessorParam> params = Lists.newArrayList();

            skipUntil(parser, paramParser.getExpectedTag());
            while (parser.getEventType() != XmlPullParser.END_TAG) {
                params.add(paramParser.parse(parser));
                skipUntil(parser, paramParser.getExpectedTag());
            }

            source.setParams(params.toArray(new SourceAccessorParam[params.size()]));

            return source;
        }

        @Override
        protected FloatAccessor handleAttribute(FloatAccessor object, String attribute, String value) {
            switch (attribute) {
                case "count":
                    object.setCount(Integer.valueOf(value));
                    break;
                case "offset":
                    object.setOffset(Integer.valueOf(value));
                    break;
                case "source":
                    object.setSource(value);
                    break;
                case "stride":
                    object.setStride(Integer.valueOf(value));
                    break;
            }
            return object;
        }
    }

    private class ParamParser extends AbstractParser<SourceAccessorParam> {
        @Override
        public String getExpectedTag() {
            return "param";
        }

        @Override
        protected SourceAccessorParam createObject(XmlPullParser parser) throws XmlPullParserException, IOException {
            return setAttributes(parser, new SourceAccessorParam());
        }

        @Override
        protected SourceAccessorParam handleAttribute(SourceAccessorParam object, String attribute, String value) {
            switch (attribute) {
                case "name":
                    object.setName(value);
                    break;
                case "sid":
                    object.setSID(value);
                    break;
                case "type":
                    object.setType(value);
                    break;
                case "semantic":
                    object.setSemantic(value);
                    break;
            }
            return object;
        }
    }
}
