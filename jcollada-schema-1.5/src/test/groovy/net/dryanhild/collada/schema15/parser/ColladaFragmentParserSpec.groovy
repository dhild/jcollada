package net.dryanhild.collada.schema15.parser

import dagger.ObjectGraph
import spock.lang.Specification

class ColladaFragmentParserSpec extends Specification implements ParserSpec {

    static ObjectGraph graph
    ColladaFragmentParser parser

    def setupSpec() {
        graph = ObjectGraph.create(new ParserModule())
    }

    def setup() {
        parser = graph.get(ColladaFragmentParser)
    }

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

    def 'asset loading is triggered'() {
        setup:
        def data = "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" version=\"1.5.0\">" +
                " <asset><modified>2007-12-11T14:24:00Z</modified></asset>" +
                "</COLLADA>"
        when:
        def result = parser.parse(URI.create("test.dae"), createDocumentParser(data))

        then:
        result.asset.modified.epochSecond
    }
}
