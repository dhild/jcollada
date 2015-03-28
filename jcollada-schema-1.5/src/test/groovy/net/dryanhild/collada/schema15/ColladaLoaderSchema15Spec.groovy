package net.dryanhild.collada.schema15

import net.dryanhild.collada.spi.ParsingContext
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class ColladaLoaderSchema15Spec extends Specification {

    static String MINIMAL = "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" version=\"1.5.0\"/>"

    ColladaLoaderSchema15 loader

    def setup() {
        loader = new ColladaLoaderSchema15()
    }

    def 'can load a minimalist file'() {
        setup:
        def context = Mock(ParsingContext)
        context.getCharset() >> StandardCharsets.UTF_8
        context.getMainFileInputStream() >> new ByteArrayInputStream(MINIMAL.bytes)

        when:
        def result = loader.load(context)

        then:
        result.version.majorVersion == 1
        result.version.minorVersion == 5
        result.version.thirdVersion == 0
    }
}
