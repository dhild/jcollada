package net.dryanhild.collada.schema15.parser

import net.dryanhild.collada.schema15.ColladaModule
import net.dryanhild.collada.schema15.parser.metadata.AssetParser
import spock.guice.UseModules
import spock.lang.Specification

import javax.inject.Inject

@UseModules(ColladaModule)
class ColladaFragmentParserSpec extends Specification implements ParserSpec {

    @Inject
    AssetParser assetParser

    @Inject
    ColladaFragmentParser parser

    def 'can parse a minimalist file'() {
        setup:
        def data = "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" version=\"1.5.0\"/>"
        when:
        def result = parser.parse(URI.create("test.dae"), createDocumentParser(data))

        then:
        noExceptionThrown()
        result != null
        result.uri == URI.create("test.dae")
    }

    def 'can interpret the base attribute'() {
        setup:
        def data = "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" version=\"1.5.0\"" +
                " base=\"http://example.org/collada/\" />"
        when:
        def result = parser.parse(URI.create("test.dae"), createDocumentParser(data))

        then:
        result.uri == URI.create("http://example.org/collada/")
    }
}
