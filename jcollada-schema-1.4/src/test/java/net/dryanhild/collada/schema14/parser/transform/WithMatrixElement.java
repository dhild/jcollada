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

import net.dryanhild.collada.data.transform.Matrix;
import net.dryanhild.collada.schema14.parser.BaseParserTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WithMatrixElement extends BaseParserTest {

    @Inject
    private MatrixParser matrixParser;

    private Matrix matrix;

    @Override
    protected String getDataString() {
        return "<matrix sid=\"transform\">1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16</matrix>";
    }

    @BeforeMethod
    public void setNode() throws IOException, XmlPullParserException {
        matrix = matrixParser.parse();
    }

    @Test
    public void matrixHasCorrectValues() {
        assertThat(matrix.getValues()).containsExactly(1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16);
    }

    @Test
    public void matrixHasCorrectSID() {
        assertThat(matrix.getSID()).isEqualTo("transform");
    }

}
