package net.dryanhild.jcollada.schema14.geometry.source;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.schema14.geometry.SourceStream;

import org.testng.annotations.Test;

@Test
public class WithSkippingAccessor extends SourceStreamTest {

    private static final String skippingXML = //
    "<source id=\"test1\" xmlns=\"http://www.collada.org/2005/11/COLLADASchema\">\n" + //
            "  <float_array name=\"values\" count=\"9\">\n" + //
            "    1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0\n" + //
            "  </float_array>\n" + //
            "  <technique_common>\n" + //
            "    <accessor source=\"#values\" count=\"3\" stride=\"3\">\n" + //
            "      <param name=\"A\" type=\"float\"/>\n" + //
            "      <param type=\"float\"/>\n" + //
            "      <param name=\"X\" type=\"float\"/>\n" + //
            "    </accessor>\n" + //
            "  </technique_common>" + //
            "</source>";

    public void oneAccess() {
        SourceStream stream = new SourceStream(getSource(skippingXML));

        double[] values = new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, };
        int elementSize = stream.fillElement("test1", 1, values, 1);

        assertThat(elementSize).isEqualTo(2);
        assertThat(values).containsExactly(Double.NaN, 4, 6, Double.NaN);
    }

    public void threeAccesses() {
        SourceStream stream = new SourceStream(getSource(skippingXML));

        double[] values = new double[6];

        int offset = 0;
        for (int i = 0; i < 3; i++) {
            offset += stream.fillElement("test1", i, values, offset);
        }

        assertThat(offset).isEqualTo(6);
        assertThat(values).containsExactly(1, 3, 4, 6, 7, 9);
    }

}