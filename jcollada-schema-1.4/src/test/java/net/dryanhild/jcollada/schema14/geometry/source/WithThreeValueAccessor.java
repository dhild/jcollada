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

package net.dryanhild.jcollada.schema14.geometry.source;

import static org.assertj.core.api.Assertions.assertThat;
import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import net.dryanhild.collada.schema14.geometry.SourceStream;

import org.testng.annotations.Test;

@Test
public class WithThreeValueAccessor extends SourceStreamTest {
    private static final String THREE_VALUE_XML = //
    "<source id=\"test1\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <float_array id=\"values\" count=\"9\">\n" + //
            "    1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0\n" + //
            "  </float_array>\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" count=\"3\" stride=\"3\">\n" + //
            "      <param name=\"A\" type=\"float\"/>\n" + //
            "      <param name=\"F\" type=\"float\"/>\n" + //
            "      <param name=\"X\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>";

    public void oneAccess() {
        SourceStream stream = new SourceStream(getSource(THREE_VALUE_XML));

        float[] values = stream.getElement("#test1", 1);

        assertThat(values).containsExactly(4, 5, 6);
    }

    public void threeAccesses() {
        SourceStream stream = new SourceStream(getSource(THREE_VALUE_XML));

        TFloatList values = new TFloatArrayList();

        for (int i = 0; i < 3; i++) {
            values.add(stream.getElement("#test1", i));
        }

        assertThat(values.toArray()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    public void badSourceNameFails() {
        SourceStream stream = new SourceStream(getSource(THREE_VALUE_XML));

        try {
            stream.getElement("#test2", 0);

            assert false : "Expected an exception!";
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessageContaining("#test2");
        }
    }

    public void indexTooLargeFails() {
        SourceStream stream = new SourceStream(getSource(THREE_VALUE_XML));

        try {
            stream.getElement("#test1", 5);

            assert false : "Expected an exception!";
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessageContaining("Index").hasMessageContaining("5").hasMessageContaining("3");
        }
    }
}
