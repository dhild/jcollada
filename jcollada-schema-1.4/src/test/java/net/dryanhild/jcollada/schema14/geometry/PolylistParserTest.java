package net.dryanhild.jcollada.schema14.geometry;

import static org.assertj.core.api.Assertions.assertThat;
import gnu.trove.list.TIntList;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import net.dryanhild.jcollada.data.geometry.DataType;

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
        InputLocalOffset normInput = polys.addNewInput();
        normInput.setSemantic(DataType.NORMAL.toString());
        normInput.setSource(NORMALS);
        normInput.setOffset(BigInteger.valueOf(1));

        List<BigInteger> vcounts = Lists.newArrayList(BigInteger.valueOf(3));
        List<BigInteger> p = Lists.newArrayList( //
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
