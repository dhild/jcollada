package net.dryanhild.jcollada.schema14.geometry.source;

import net.dryanhild.jcollada.schema14.geometry.SourceStream;

import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;
import org.testng.annotations.Test;

/**
 * The sources are set up to produce the same results as using multiple
 * accessors, but with skipping parameters instead of offsets.
 */
@Test
public class WithMultipleAccessorsSingleSourceSkipping extends WithMultipleAccessorsSingleSource {

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
    private static final String sourceTexture1 = //
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
    private static final String sourceTexture2 = //
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
        Source pos = getSource(sourcePositions);
        Source nor = getSource(sourceNormals);
        Source tex1 = getSource(sourceTexture1);
        Source tex2 = getSource(sourceTexture2);
        return new SourceStream(pos, nor, tex1, tex2);
    }

}
