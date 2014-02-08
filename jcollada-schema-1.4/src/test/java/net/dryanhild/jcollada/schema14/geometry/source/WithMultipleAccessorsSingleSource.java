package net.dryanhild.jcollada.schema14.geometry.source;

import static org.assertj.core.api.Assertions.assertThat;
import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import net.dryanhild.jcollada.schema14.geometry.SourceStream;

import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;
import org.testng.annotations.Test;

@Test
public class WithMultipleAccessorsSingleSource extends SourceStreamTest {

    private static final String sourcePositions = //
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
    private static final String sourceNormals = //
    "<source id=\"normals\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" offset=\"3\" count=\"3\" stride=\"10\">\n" + //
            "      <param name=\"NOT\" type=\"float\"/>\n" + //
            "      <param name=\"SIGNIFICANT\" type=\"float\"/>\n" + //
            "      <param name=\"X\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>\n";
    private static final String sourceTexture1 = //
    "<source id=\"texture1\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" offset=\"6\" count=\"3\" stride=\"10\">\n" + //
            "      <param name=\"A\" type=\"float\"/>\n" + //
            "      <param name=\"F\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>\n";
    private static final String sourceTexture2 = //
    "<source id=\"texture2\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" offset=\"8\"  count=\"3\" stride=\"10\">\n" + //
            "      <param name=\"Q\" type=\"float\"/>\n" + //
            "      <param name=\"F\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>\n" + //
            "</source>";

    protected SourceStream makeStream() {
        Source pos = getSource(sourcePositions);
        Source nor = getSource(sourceNormals);
        Source tex1 = getSource(sourceTexture1);
        Source tex2 = getSource(sourceTexture2);
        return new SourceStream(pos, nor, tex1, tex2);
    }

    public void oneAccessPosition() {
        SourceStream stream = makeStream();

        float[] values = stream.getElement("#positions", 1);

        assertThat(values).containsExactly(11, 12, 13);
    }

    public void oneAccessNormals() {
        SourceStream stream = makeStream();

        float[] values = stream.getElement("#normals", 1);

        assertThat(values).containsExactly(14, 15, 16);
    }

    public void oneAccessTex1() {
        SourceStream stream = makeStream();

        float[] values = stream.getElement("#texture1", 1);

        assertThat(values).containsExactly(17, 18);
    }

    public void oneAccessTex2() {
        SourceStream stream = makeStream();

        float[] values = stream.getElement("#texture2", 1);

        assertThat(values).containsExactly(19, 20);
    }

    public void accessEverything() {
        SourceStream stream = makeStream();

        TFloatList values = new TFloatArrayList();
        for (int i = 0; i < 2; i++) {
            values.add(stream.getElement("#positions", i));
            values.add(stream.getElement("#normals", i));
            values.add(stream.getElement("#texture1", i));
            values.add(stream.getElement("#texture2", i));
        }

        assertThat(values.toArray()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20);
    }

    public void noSourceArrayFails() {
        Source nor = getSource(sourceNormals);
        Source tex1 = getSource(sourceTexture1);
        Source tex2 = getSource(sourceTexture2);

        try {
            new SourceStream(nor, tex1, tex2);

            assert false : "Expected an exception!";
        } catch (IllegalStateException e) {
            assertThat(e).hasMessageContaining("#values");
        }
    }

}
