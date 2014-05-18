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

import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import net.dryanhild.collada.schema14.ParsingData;
import net.dryanhild.collada.schema14.data.AbstractNameableAddressableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public abstract class AbstractParser<OutputType> implements XmlParser<OutputType> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractParser.class);

    @Inject
    protected ParsingData data;

    public abstract String getExpectedTag();

    protected void validate() throws XmlPullParserException, IOException {
        data.parser.require(START_TAG, null, getExpectedTag());
    }

    public final OutputType parse() throws XmlPullParserException, IOException {
        validate();
        final int depth = data.parser.getDepth();
        OutputType output = createObject();

        int token = data.parser.next();
        while (depth <= data.parser.getDepth()) {
            if (token == START_TAG) {
                int line = data.parser.getLineNumber();
                int col = data.parser.getColumnNumber();
                handleChildElement(output, data.parser.getName());
                if (line == data.parser.getLineNumber() && col == data.parser.getColumnNumber()) {
                    skipElement();
                }
                token = data.parser.getEventType();
            } else {
                token = data.parser.next();
            }
        }

        return finishObject(output);
    }

    protected abstract OutputType createObject() throws XmlPullParserException, IOException;

    protected OutputType finishObject(OutputType object) throws XmlPullParserException, IOException {
        return object;
    }

    protected OutputType setAttributes(@NotNull OutputType object)
            throws XmlPullParserException, IOException {
        data.parser.require(START_TAG, null, getExpectedTag());

        for (int i = 0; i < data.parser.getAttributeCount(); i++) {
            String name = data.parser.getAttributeName(i);
            String value = data.parser.getAttributeValue(i);
            setBasicAttributes(object, name, value);
            handleAttribute(object, name, value);
        }
        return object;
    }

    protected void setBasicAttributes(@NotNull OutputType object, String attribute, String value) {
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

    protected void handleAttribute(@NotNull OutputType object, String attribute, String value) {
        // By default, ignores most attributes.
    }

    protected void skipElement() throws XmlPullParserException, IOException {
        data.parser.require(START_TAG, null, null);

        logger.trace("Skipping element [{}]{}", data.parser.getNamespace(), data.parser.getName());

        final int depth = data.parser.getDepth();
        while (data.parser.getDepth() >= depth) {
            data.parser.next();
        }

        logger.trace("Finished skipping element");
    }

    protected void handleChildElement(OutputType parent, String childTag)
            throws IOException, XmlPullParserException {
        // Skip by default.
        skipElement();
    }

    protected TFloatList readFloats() throws XmlPullParserException, IOException {
        data.parser.require(START_TAG, null, null);
        data.parser.next();
        data.parser.require(TEXT, null, null);

        TFloatList floats = new TFloatArrayList();

        int[] startAndLength = new int[2];
        char[] text = data.parser.getTextCharacters(startAndLength);
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

    protected TIntList readInts() throws XmlPullParserException, IOException {
        data.parser.require(START_TAG, null, null);
        data.parser.next();
        data.parser.require(TEXT, null, null);

        TIntList ints = new TIntArrayList();

        int[] startAndLength = new int[2];
        char[] text = data.parser.getTextCharacters(startAndLength);
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
                ints.add(Integer.valueOf(new String(text, i, count)));
            }
            i += count;
        }

        return ints;
    }
}
