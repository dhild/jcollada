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

import net.dryanhild.collada.schema14.ParsingData;
import net.dryanhild.collada.schema14.data.ColladaDocument14;
import org.jvnet.testing.hk2testng.HK2;
import org.testng.annotations.BeforeMethod;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringReader;

@HK2(enablePerThread = true)
public abstract class BaseParserTest {

    protected abstract String getDataString();

    @Inject
    protected ParsingData data;

    @BeforeMethod
    public void resetParser() throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        data.reset(factory.newPullParser());
        StringReader reader = new StringReader(getDataString());
        data.parser.setInput(reader);
        data.document = new ColladaDocument14();

        while (data.parser.getEventType() != XmlPullParser.START_TAG) {
            data.parser.next();
        }
    }

}
