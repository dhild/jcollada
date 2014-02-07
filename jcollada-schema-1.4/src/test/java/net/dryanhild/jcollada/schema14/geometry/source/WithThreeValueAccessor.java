package net.dryanhild.jcollada.schema14.geometry.source;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.schema14.geometry.SourceStream;

import org.testng.annotations.Test;

@Test
public class WithThreeValueAccessor extends SourceStreamTest {
    private static final String threeValueXML = //
    "<source id=\"test1\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <float_array name=\"values\" count=\"9\">\n" + //
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
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        double[] values = new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, };
        int elementSize = stream.fillElement("test1", 1, values, 1);

        assertThat(elementSize).isEqualTo(3);
        assertThat(values).containsExactly(Double.NaN, 4, 5, 6, Double.NaN);
    }

    public void threeAccesses() {
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        double[] values = new double[9];

        int offset = 0;
        for (int i = 0; i < 3; i++) {
            offset += stream.fillElement("test1", i, values, offset);
        }

        assertThat(offset).isEqualTo(9);
        assertThat(values).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    public void badSourceNameFails() {
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        try {
            stream.fillElement("test2", 0, new double[3], 0);

            assert false : "Expected an exception!";
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessageContaining("test2");
        }
    }

    public void indexTooLargeFails() {
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        try {
            stream.fillElement("test1", 5, new double[3], 0);

            assert false : "Expected an exception!";
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessageContaining("Index").hasMessageContaining("5").hasMessageContaining("3");
        }
    }
}
