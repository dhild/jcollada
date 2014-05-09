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

import net.dryanhild.collada.schema14.data.geometry.source.FloatArray;
import net.dryanhild.collada.schema14.parser.BaseParserTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WithFloatArray extends BaseParserTest {

    @Inject
    private FloatArrayParser floatArrayParser;

    private FloatArray floatArray;

    @Override
    protected String getDataString() {
        return "<float_array id=\"testId\" count=\"9\">0 0 -1 1 0 -1 0 1 -1</float_array>";
    }

    @BeforeMethod
    public void setNode() throws IOException, XmlPullParserException {
        floatArray = floatArrayParser.parse();
    }

    @Test
    public void idIsCorrect() {
        assertThat(floatArray.getId()).isEqualTo("testId");
    }

    @Test
    public void countIsCorrect() {
        assertThat(floatArray.getValues()).hasSize(9);
    }

    @Test
    public void dataIsCorrect() {
        assertThat(floatArray.getValues()).containsExactly(0, 0, -1, 1, 0, -1, 0, 1, -1);
    }
}
