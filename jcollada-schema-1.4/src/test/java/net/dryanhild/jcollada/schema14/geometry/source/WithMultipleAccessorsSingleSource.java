package net.dryanhild.jcollada.schema14.geometry.source;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.schema14.geometry.SourceStream;

import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;
import org.testng.annotations.Test;

@Test
public class WithMultipleAccessorsSingleSource extends SourceStreamTest {

    private static final String sourcePositions = //
    "<source id=\"positions\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <float_array name=\"values\" count=\"30\">\n" + //
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

        double[] values = new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN };
        int elementSize = stream.fillElement("positions", 1, values, 1);

        assertThat(elementSize).isEqualTo(3);
        assertThat(values).containsExactly(Double.NaN, 11, 12, 13, Double.NaN);
    }

    public void oneAccessNormals() {
        SourceStream stream = makeStream();

        double[] values = new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN };
        int elementSize = stream.fillElement("normals", 1, values, 1);

        assertThat(elementSize).isEqualTo(3);
        assertThat(values).containsExactly(Double.NaN, 14, 15, 16, Double.NaN);
    }

    public void oneAccessTex1() {
        SourceStream stream = makeStream();

        double[] values = new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, };
        int elementSize = stream.fillElement("texture1", 1, values, 1);

        assertThat(elementSize).isEqualTo(2);
        assertThat(values).containsExactly(Double.NaN, 17, 18, Double.NaN);
    }

    public void oneAccessTex2() {
        SourceStream stream = makeStream();

        double[] values = new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, };
        int elementSize = stream.fillElement("texture2", 1, values, 1);

        assertThat(elementSize).isEqualTo(2);
        assertThat(values).containsExactly(Double.NaN, 19, 20, Double.NaN);
    }

    public void accessEverything() {
        SourceStream stream = makeStream();

        double[] values = new double[20];
        int offset = 0;
        for (int i = 0; i < 2; i++) {
            offset += stream.fillElement("positions", i, values, offset);
            offset += stream.fillElement("normals", i, values, offset);
            offset += stream.fillElement("texture1", i, values, offset);
            offset += stream.fillElement("texture2", i, values, offset);
        }

        assertThat(offset).isEqualTo(20);
        assertThat(values).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
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
