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

import net.dryanhild.collada.schema14.geometry.SourceStream;

import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;
import org.testng.annotations.Test;

/**
 * The sources are set up to produce the same results as using multiple
 * accessors, but with skipping parameters instead of offsets.
 */
@Test
public class WithMultipleAccessorsSingleSourceSkipping extends WithMultipleAccessorsSingleSource {

    private static final String SOURCE_POSITIONS = //
    "<source id=\"positions\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <float_array id=\"values\" count=\"30\">\n" + //
            "    1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30\n" + //
            "  </float_array>\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" count=\"3\" stride=\"10\">\n" + //
            "      <param name=\"NAME\" type=\"float\"/>\n" + //
            "      <param name=\"VALUES\" type=\"float\"/>\n" + //
            "      <param name=\"ARE\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>\n";
    private static final String SOURCE_NORMALS = //
    "<source id=\"normals\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" count=\"3\" stride=\"10\">\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param name=\"A\" type=\"float\"/>\n" + //
            "      <param name=\"F\" type=\"float\"/>\n" + //
            "      <param name=\"X\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>\n";
    private static final String SOURCE_TEXTURE_1 = //
    "<source id=\"texture1\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" count=\"3\" stride=\"10\">\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param name=\"A\" type=\"float\"/>\n" + //
            "      <param name=\"F\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>\n";
    private static final String SOURCE_TEXTURE_2 = //
    "<source id=\"texture2\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" count=\"3\" stride=\"10\">\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param name=\"Q\" type=\"float\"/>\n" + //
            "      <param name=\"F\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>";

    @Override
    protected SourceStream makeStream() {
        Source pos = getSource(SOURCE_POSITIONS);
        Source nor = getSource(SOURCE_NORMALS);
        Source tex1 = getSource(SOURCE_TEXTURE_1);
        Source tex2 = getSource(SOURCE_TEXTURE_2);
        return new SourceStream(pos, nor, tex1, tex2);
    }

}
