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

package net.dryanhild.collada.schema14;

import com.google.common.collect.ImmutableList;
import net.dryanhild.collada.ParsingException;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema14.data.ColladaDocument14;
import net.dryanhild.collada.schema14.parser.XmlParser;
import net.dryanhild.collada.schema14.postprocessors.Postprocessor;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.regex.Pattern;

public class ColladaLoaderService14 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_4_0 = new VersionSupport(1, 4, 0);
    public static final VersionSupport VERSION_1_4_1 = new VersionSupport(1, 4, 1);

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            ".*COLLADA[^>]+version\\s?=\\s?\\\"1\\.4\\.[01]\\\".*", Pattern.DOTALL);

    private ColladaServiceRegistry serviceRegistry = new ColladaServiceRegistry();

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_4_0, VERSION_1_4_1);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        return VERSION_PATTERN.matcher(header).matches();
    }

    @Override
    public ColladaDocument load(ParsingContext context) throws IOException {
        ParsingData data = serviceRegistry.getParsingData();
        parseFile(context, data);

        for (Postprocessor proc : data.postprocessors) {
            proc.process();
        }

        return data.document;
    }

    private void parseFile(ParsingContext context, ParsingData data) throws IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(context.isValidating());
            XmlPullParser parser = factory.newPullParser();
            Reader reader = new InputStreamReader(context.getMainFileInputStream(), context.getCharset());
            parser.setInput(reader);

            data.reset(parser);
            XmlParser<ColladaDocument14> xmlParser = serviceRegistry.getDocumentParser();
            xmlParser.parse();
        } catch (XmlPullParserException e) {
            throw new ParsingException("Unable to parse document!", e);
        }
    }
}
