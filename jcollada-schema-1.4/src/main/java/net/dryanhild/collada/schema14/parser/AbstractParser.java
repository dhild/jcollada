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

import com.google.common.collect.Sets;
import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import net.dryanhild.collada.schema14.data.AbstractNameableAddressableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Set;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public abstract class AbstractParser<OutputType> implements XmlParser<OutputType> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractParser.class);

    public abstract String getExpectedTag();

    protected void validate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(START_TAG, null, getExpectedTag());
    }

    public final OutputType parse(XmlPullParser parser) throws XmlPullParserException, IOException {
        validate(parser);
        final int depth = parser.getDepth();
        OutputType output = createObject(parser);

        if (parser.getEventType() == END_TAG && depth == parser.getDepth()) {
            return finishObject(output);
        }

        int token = parser.next();
        while (depth <= parser.getDepth()) {
            if (token == START_TAG) {
                handleChildElement(parser, output, parser.getName());
                token = parser.getEventType();
                if (token == START_TAG) {
                    skipElement(parser);
                    token = parser.getEventType();
                }
            } else {
                token = parser.next();
            }
        }

        return finishObject(output);
    }

    protected abstract OutputType createObject(XmlPullParser parser) throws XmlPullParserException, IOException;

    protected OutputType finishObject(OutputType object) throws XmlPullParserException, IOException {
        return object;
    }

    protected OutputType setAttributes(XmlPullParser parser, OutputType object)
            throws XmlPullParserException, IOException {
        parser.require(START_TAG, null, getExpectedTag());

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            setBasicAttributes(object, name, value);
            object = handleAttribute(object, name, value);
        }
        return object;
    }

    protected void setBasicAttributes(OutputType object, String attribute, String value) {
        if (object != null) {
            switch (attribute) {
                case "id":
                    if (AbstractNameableAddressableType.class.isAssignableFrom(object.getClass())) {
                        ((AbstractNameableAddressableType) object).setId(value);
                    }
                    break;
                case "name":
                    if (AbstractNameableAddressableType.class.isAssignableFrom(object.getClass())) {
                        ((AbstractNameableAddressableType) object).setName(value);
                    }
            }
        }
    }

    protected OutputType handleAttribute(OutputType object, String attribute, String value) {
        // By default, ignores most attributes.
        return object;
    }

    protected void skipElement(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(START_TAG, null, null);

        logger.trace("Skipping element {%s}%s", parser.getNamespace(), parser.getName());

        final int depth = parser.getDepth();
        while (parser.getDepth() >= depth) {
            parser.next();
        }

        logger.trace("Finished kipping element {%s}%s", parser.getNamespace(), parser.getName());
    }

    protected void skipUntil(XmlPullParser parser, String... tags) throws IOException, XmlPullParserException {
        Set<String> tagSet = Sets.newHashSet(tags);

        int type = parser.getEventType();
        if (type == END_TAG) {
            type = parser.next();
        }

        final int depth = parser.getDepth();
        while (parser.getDepth() >= depth) {
            if (type == START_TAG) {
                if (tagSet.contains(parser.getName())) {
                    return;
                }
                skipElement(parser);
            } else {
                type = parser.next();
            }
        }
    }

    protected void handleChildElement(XmlPullParser parser, OutputType parent, String childTag)
            throws IOException, XmlPullParserException {
        // Skip by default.
        skipElement(parser);
    }

    protected TFloatList readFloats(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(TEXT, null, null);

        TFloatList floats = new TFloatArrayList();

        int[] startAndLength = new int[2];
        char[] text = parser.getTextCharacters(startAndLength);
        final int maxIndex = startAndLength[0] + startAndLength[1];
        int i = startAndLength[0];
        while (i < (startAndLength[0] + startAndLength[1])) {
            while (Character.isWhitespace(text[i])) {
                i++;
            }
            int count = 0;
            while (!Character.isWhitespace(text[i + count]) && (i + count) < maxIndex) {
                count++;
            }
            if (count > 0) {
                floats.add(Float.valueOf(new String(text, i, count)));
            }
            i += count;
        }

        return floats;
    }
}
