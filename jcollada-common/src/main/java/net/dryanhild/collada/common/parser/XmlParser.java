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

package net.dryanhild.collada.common.parser;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.SneakyThrows;
import net.dryanhild.collada.ParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.function.BiConsumer;

import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class XmlParser<OutputType> {

    public static XmlPullParser createPullParser(boolean validating, InputStream inputStream, Charset charset)
            throws IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(validating);
            XmlPullParser parser = factory.newPullParser();
            Reader reader = new InputStreamReader(inputStream, charset);
            parser.setInput(reader);
            return parser;
        } catch (XmlPullParserException e) {
            throw new ParsingException("Unable to parse document!", e);
        }
    }

    private final Logger logger;

    private final String expectedTag;
    private final Multimap<String, BiConsumer<OutputType, String>> attributeConsumers = HashMultimap.create();
    private final Multimap<String, BiConsumer<OutputType, XmlPullParser>> elementConsumers = HashMultimap.create();

    public XmlParser(String tag, Class<OutputType> type) {
        logger = LoggerFactory.getLogger(XmlParser.class.getName() + "." + type.getName());
        expectedTag = tag;
        try {
            Method m = type.getMethod("setId", String.class);
            attributeConsumers.put("id", (object, value) -> trySet(m, object, value));
        } catch (NoSuchMethodException e) {
            logger.debug("No method setId(String) for {}", type.getName());
        }
        try {
            Method m = type.getMethod("setName", String.class);
            attributeConsumers.put("name", (object, value) -> trySet(m, object, value));
        } catch (NoSuchMethodException e) {
            logger.debug("No method setName(String) for {}", type.getName());
        }
        try {
            Method m = type.getMethod("setSid", String.class);
            attributeConsumers.put("sid", (object, value) -> trySet(m, object, value));
        } catch (NoSuchMethodException e) {
            logger.debug("No method setSid(String) for {}", type.getName());
        }
    }

    public void addAttributeConsumer(String tag, BiConsumer<OutputType, String> consumer) {
        attributeConsumers.put(tag, consumer);
    }

    public void addElementConsumer(String tag, BiConsumer<OutputType, XmlPullParser> consumer) {
        elementConsumers.put(tag, consumer);
    }

    private void trySet(Method method, Object object, Object... args) {
        try {
            method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.debug("Tried to invoke " + method + " on " + object, e);
        }
    }

    @SneakyThrows({IOException.class, XmlPullParserException.class})
    public final OutputType parse(XmlPullParser parser, OutputType object) {
        parser.require(START_TAG, null, expectedTag);
        final int depth = parser.getDepth();

        setAttributes(parser, object);

        int token = parser.next();
        while (depth <= parser.getDepth()) {
            if (token == START_TAG) {
                token = processElement(parser, object);
            } else {
                token = parser.next();
            }
        }

        return object;
    }

    @SneakyThrows({IOException.class, XmlPullParserException.class})
    protected void setAttributes(XmlPullParser parser, OutputType object) {
        parser.require(START_TAG, null, expectedTag);

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            for (BiConsumer<OutputType, String> consumer : attributeConsumers.get(name)) {
                consumer.accept(object, value);
            }
        }
    }

    @SneakyThrows({XmlPullParserException.class})
    private int processElement(XmlPullParser parser, OutputType object) {
        final int line = parser.getLineNumber();
        final int col = parser.getColumnNumber();

        for (BiConsumer<OutputType, XmlPullParser> consumer : elementConsumers.get(parser.getName())) {
            consumer.accept(object, parser);
        }
        if (line == parser.getLineNumber() && col == parser.getColumnNumber()) {
            skipElement(parser);
        }
        return parser.getEventType();
    }

    @SneakyThrows({IOException.class, XmlPullParserException.class})
    protected void skipElement(XmlPullParser parser) {
        parser.require(START_TAG, null, null);

        logger.trace("Skipping element [{}]{}", parser.getNamespace(), parser.getName());

        final int depth = parser.getDepth();
        while (parser.getDepth() >= depth) {
            parser.next();
        }

        logger.trace("Finished skipping element");
    }
}
