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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractParser<OutputType> implements XmlParser<OutputType> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractParser.class);

    protected int getExpectedEventType() {
        return XmlPullParser.START_TAG;
    }

    protected abstract String getExpectedTag();

    protected void validate(XmlPullParser parser) throws XmlPullParserException, IOException {
        Preconditions.checkState(parser.getEventType() == getExpectedEventType());
        Preconditions.checkState(parser.getName().equals(getExpectedTag()));

        validateImpl(parser);
    }

    protected void validateImpl(XmlPullParser parser) throws XmlPullParserException, IOException {
    }

    public final OutputType parse(XmlPullParser parser) throws XmlPullParserException, IOException {
        validate(parser);
        OutputType output = parseImpl(parser);

        int token = parser.getEventType();
        while (token != XmlPullParser.END_TAG) {
            if (token == XmlPullParser.START_TAG) {
                skipElement(parser);
            } else {
                parser.next();
                token = parser.getEventType();
            }
        }

        return output;
    }

    protected abstract OutputType parseImpl(XmlPullParser parser) throws XmlPullParserException, IOException;

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

    protected void skipUntil(XmlPullParser parser, String ... tags) throws IOException, XmlPullParserException {
        Set<String> tagSet = Sets.newHashSet(tags);
        while (!tagSet.contains(parser.getName())) {
            skipElement(parser);
        }
    }
}
