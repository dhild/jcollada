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

package net.dryanhild.collada.schema14.parser.transform;

import gnu.trove.list.TFloatList;
import net.dryanhild.collada.schema14.data.transform.MatrixImpl;
import net.dryanhild.collada.schema14.parser.AbstractParser;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

@Service
public class MatrixParser extends AbstractParser<MatrixImpl> {

    @Override
    public String getExpectedTag() {
        return "matrix";
    }

    @Override
    protected MatrixImpl createObject() throws XmlPullParserException, IOException {
        MatrixImpl matrix = setAttributes(new MatrixImpl());

        while (data.parser.getEventType() != XmlPullParser.TEXT) {
            data.parser.next();
        }
        TFloatList floatList = readFloats();

        transposeIntoColumnMajor(floatList, matrix.getValues());

        return matrix;
    }

    @Override
    protected MatrixImpl handleAttribute(MatrixImpl object, String attribute, String value) {
        if (attribute.equals("sid")) {
            object.setSID(value);
        }
        return object;
    }

    private void transposeIntoColumnMajor(TFloatList floatList, float[] values) {
        assert floatList.size() == 16;
        values[0] = floatList.get(0);
        values[1] = floatList.get(4);
        values[2] = floatList.get(8);
        values[3] = floatList.get(12);
        values[4] = floatList.get(1);
        values[5] = floatList.get(5);
        values[6] = floatList.get(9);
        values[7] = floatList.get(13);
        values[8] = floatList.get(2);
        values[9] = floatList.get(6);
        values[10] = floatList.get(10);
        values[11] = floatList.get(14);
        values[12] = floatList.get(3);
        values[13] = floatList.get(7);
        values[14] = floatList.get(11);
        values[15] = floatList.get(15);
    }

}
