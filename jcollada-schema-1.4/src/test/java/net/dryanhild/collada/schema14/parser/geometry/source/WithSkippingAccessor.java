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

import net.dryanhild.collada.schema14.data.geometry.source.FloatSource;
import net.dryanhild.collada.schema14.parser.BaseParserTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WithSkippingAccessor extends BaseParserTest {

    @Inject
    private SourceParser sourceParser;

    private FloatSource floatSource;

    @Override
    protected String getDataString() {
        return "<source id=\"test1\">\n" + //
               "  <float_array id=\"values\" count=\"9\">\n" + //
               "    1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0\n" + //
               "  </float_array>\n" + //
               "  <technique_common>\n" + //
               "    <accessor source=\"#values\" count=\"3\" stride=\"3\">\n" + //
               "      <param name=\"A\" type=\"float\"/>\n" + //
               "      <param type=\"float\"/>\n" + //
               "      <param name=\"X\" type=\"float\"/>\n" + //
               "    </accessor>\n" + //
               "  </technique_common>\n" + //
               "</source>";
    }

    @BeforeMethod
    public void setFloatSource() throws IOException, XmlPullParserException {
        floatSource = sourceParser.parse();
    }

    @Test
    public void idIsCorrect() {
        assertThat(floatSource.getId()).isEqualTo("test1");
    }

    @Test
    public void floatArrayExists() {
        assertThat(floatSource.getSource().values).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void floatArrayHasCorrectId() {
        assertThat(floatSource.getSource().getId()).isEqualTo("values");
    }

    @Test
    public void countIsCorrect() {
        assertThat(floatSource.getCommonAccessor().getCount()).isEqualTo(3);
    }

    @Test
    public void strideIsCorrect() {
        assertThat(floatSource.getCommonAccessor().getStride()).isEqualTo(3);
    }

    @Test
    public void correctParamsExist() {
        assertThat(floatSource.getCommonAccessor().getParams()).extracting("name").containsExactly("A", null, "X");
        assertThat(floatSource.getCommonAccessor().getParams()).extracting("type")
                .containsExactly("float", "float", "float");
    }
}
