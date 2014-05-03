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

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.schema14.data.SoftReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractParser<OutputType> implements XmlParser<OutputType> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractParser.class);

    public abstract String getExpectedTag();

    protected void validate(XmlPullParser parser) throws XmlPullParserException, IOException {
        Preconditions.checkState(parser.getEventType() == XmlPullParser.START_TAG);
        Preconditions.checkState(parser.getName().equals(getExpectedTag()));

        validateImpl(parser);
    }

    protected void validateImpl(XmlPullParser parser) throws XmlPullParserException, IOException {
    }

    public final OutputType parse(XmlPullParser parser) throws XmlPullParserException, IOException {
        validate(parser);
        OutputType output = createObject(parser);

        if (parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals(getExpectedTag())) {
            return output;
        }

        int token = parser.next();
        while (token != XmlPullParser.END_DOCUMENT) {
            if (token == XmlPullParser.END_TAG && parser.getName().equals(getExpectedTag())) {
                break;
            }
            if (token == XmlPullParser.START_TAG) {
                handleChildElement(parser, output, parser.getName());
                token = parser.getEventType();
            } else {
                token = parser.next();
            }
        }

        return output;
    }

    protected abstract OutputType createObject(XmlPullParser parser) throws XmlPullParserException, IOException;

    protected OutputType createProxyByUrl(XmlPullParser parser, Class<OutputType> typeClass) throws XmlPullParserException {
        Preconditions.checkState(parser.getEventType() == XmlPullParser.START_TAG);

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            if (name.equals("url")) {
                String url = parser.getAttributeValue(i);
                return SoftReference.createSoftReference(url, typeClass);
            }
        }
        throw new IncorrectFormatException(String.format("Expected tag %s to have a url attribute", getExpectedTag()));
    }

    protected OutputType setAttributes(XmlPullParser parser, OutputType object) throws XmlPullParserException {
        Preconditions.checkState(parser.getEventType() == XmlPullParser.START_TAG);

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            object = handleAttribute(object, name, value);
        }
        return object;
    }

    protected OutputType handleAttribute(OutputType object, String attribute, String value) {
        // By default, ignores attributes.
        return object;
    }

    protected void skipElement(XmlPullParser parser) throws XmlPullParserException, IOException {
        Preconditions.checkState(parser.getEventType() == XmlPullParser.START_TAG);

        logger.trace("Skipping element {%s}%s", parser.getNamespace(), parser.getName());

        int tokenCount = 0;
        int token = parser.next();
        while (token != XmlPullParser.END_TAG || tokenCount > 0) {
            if (token == XmlPullParser.START_TAG) {
                tokenCount++;
            }
            if (token == XmlPullParser.END_TAG) {
                tokenCount--;
            }

            token = parser.next();
        }

        logger.trace("Finished kipping element {%s}%s", parser.getNamespace(), parser.getName());
    }

    protected void skipUntil(XmlPullParser parser, String... tags) throws IOException, XmlPullParserException {
        Set<String> tagSet = Sets.newHashSet(tags);
        while (!tagSet.contains(parser.getName())) {
            skipElement(parser);
        }
    }

    protected void handleChildElement(XmlPullParser parser, OutputType parent, String childTag) throws IOException, XmlPullParserException {
        // Skip by default.
        skipElement(parser);
    }

    protected TFloatList readFloats(XmlPullParser parser) throws XmlPullParserException {
        Preconditions.checkState(parser.getEventType() == XmlPullParser.TEXT);

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
            floats.add(Float.valueOf(new String(text, i, count)));
            i += count;
        }

        return floats;
    }
}
