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

import gnu.trove.list.TFloatList;
import net.dryanhild.collada.schema14.data.geometry.source.FloatArray;
import net.dryanhild.collada.schema14.parser.AbstractParser;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

@Service
public class FloatArrayParser extends AbstractParser<FloatArray> {

    @Override
    public String getExpectedTag() {
        return "float_array";
    }

    @Override
    protected FloatArray createObject() throws XmlPullParserException, IOException {
        FloatArray array = setAttributes(new FloatArray());

        TFloatList floatList = readFloats();
        float[] values = array.getValues();
        assert values.length == floatList.size();
        floatList.toArray(values);

        return array;
    }

    @Override
    protected void handleAttribute(FloatArray object, String attribute, String value) {
        switch (attribute) {
            case "count":
                object.setSize(Integer.valueOf(value));
        }
    }
}
