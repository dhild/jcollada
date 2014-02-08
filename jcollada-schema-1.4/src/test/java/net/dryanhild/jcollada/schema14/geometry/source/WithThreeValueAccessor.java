package net.dryanhild.jcollada.schema14.geometry.source;

import static org.assertj.core.api.Assertions.assertThat;
import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import net.dryanhild.jcollada.schema14.geometry.SourceStream;

import org.testng.annotations.Test;

@Test
public class WithThreeValueAccessor extends SourceStreamTest {
    private static final String threeValueXML = //
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
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        float[] values = stream.getElement("#test1", 1);

        assertThat(values).containsExactly(4, 5, 6);
    }

    public void threeAccesses() {
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        TFloatList values = new TFloatArrayList();

        for (int i = 0; i < 3; i++) {
            values.add(stream.getElement("#test1", i));
        }

        assertThat(values.toArray()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    public void badSourceNameFails() {
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        try {
            stream.getElement("#test2", 0);

            assert false : "Expected an exception!";
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessageContaining("#test2");
        }
    }

    public void indexTooLargeFails() {
        SourceStream stream = new SourceStream(getSource(threeValueXML));

        try {
            stream.getElement("#test1", 5);

            assert false : "Expected an exception!";
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessageContaining("Index").hasMessageContaining("5").hasMessageContaining("3");
        }
    }
}
