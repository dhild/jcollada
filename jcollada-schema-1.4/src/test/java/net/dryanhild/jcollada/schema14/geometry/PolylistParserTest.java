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

package net.dryanhild.jcollada.schema14.geometry;

import static org.assertj.core.api.Assertions.assertThat;
import gnu.trove.list.TIntList;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import net.dryanhild.collada.data.geometry.DataType;
import net.dryanhild.collada.schema14.geometry.IndexReorganizer;
import net.dryanhild.collada.schema14.geometry.PolylistParser;
import net.dryanhild.collada.schema14.geometry.SourceReference;

import org.collada.x2005.x11.colladaSchema.InputLocalOffset;
import org.collada.x2005.x11.colladaSchema.PolylistDocument.Polylist;
import org.collada.x2005.x11.colladaSchema.VerticesDocument.Vertices;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

@Test
public class PolylistParserTest {

    private static final String NORMALS = "normals";
    private static final String POSITIONS = "positions";

    public void oneTriangleOneType() {
        IndexReorganizer reorganizer = new IndexReorganizer(0);
        Vertices vertices = Vertices.Factory.newInstance();
        Polylist polys = Polylist.Factory.newInstance();

        InputLocalOffset posInput = polys.addNewInput();
        posInput.setSemantic(DataType.POSITION.toString());
        posInput.setSource(POSITIONS);
        posInput.setOffset(BigInteger.valueOf(0));

        List<BigInteger> vcounts = Lists.newArrayList(BigInteger.valueOf(3));
        List<BigInteger> p = Lists.newArrayList(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3));

        polys.setVcount(vcounts);
        polys.setP(p);

        PolylistParser parser = new PolylistParser(polys, vertices, reorganizer);
        int[] indices = parser.getTriangleIndices();

        Map<SourceReference, TIntList> elements = reorganizer.getIndicesByElement();

        int[] positions = elements.get(new SourceReference(DataType.POSITION, POSITIONS)).toArray();

        assertThat(indices).contains(0, 1, 2);
        assertThat(positions).containsExactly(1, 2, 3);
    }

    public void oneTriangleTwoTypes() {
        IndexReorganizer reorganizer = new IndexReorganizer(0);
        Vertices vertices = Vertices.Factory.newInstance();
        Polylist polys = Polylist.Factory.newInstance();

        InputLocalOffset posInput = polys.addNewInput();
        posInput.setSemantic(DataType.POSITION.toString());
        posInput.setSource(POSITIONS);
        posInput.setOffset(BigInteger.valueOf(0));
        InputLocalOffset normInput = polys.addNewInput();
        normInput.setSemantic(DataType.NORMAL.toString());
        normInput.setSource(NORMALS);
        normInput.setOffset(BigInteger.valueOf(1));

        List<BigInteger> vcounts = Lists.newArrayList(BigInteger.valueOf(3));
        List<BigInteger> p = Lists.newArrayList(//
                BigInteger.valueOf(1), BigInteger.valueOf(3), //
                BigInteger.valueOf(2), BigInteger.valueOf(2), //
                BigInteger.valueOf(3), BigInteger.valueOf(1));

        polys.setVcount(vcounts);
        polys.setP(p);

        PolylistParser parser = new PolylistParser(polys, vertices, reorganizer);
        int[] indices = parser.getTriangleIndices();

        Map<SourceReference, TIntList> elements = reorganizer.getIndicesByElement();

        int[] positions = elements.get(new SourceReference(DataType.POSITION, POSITIONS)).toArray();
        int[] normals = elements.get(new SourceReference(DataType.NORMAL, NORMALS)).toArray();

        assertThat(indices).contains(0, 1, 2);
        assertThat(positions).containsExactly(1, 2, 3);
        assertThat(normals).containsExactly(3, 2, 1);
    }
}
