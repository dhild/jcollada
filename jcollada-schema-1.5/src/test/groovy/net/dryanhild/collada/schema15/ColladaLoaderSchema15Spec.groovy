package net.dryanhild.collada.schema15

import net.dryanhild.collada.schema15.parser.ColladaFragmentParser
import net.dryanhild.collada.schema15.postprocess.DocumentAssembler
import net.dryanhild.collada.spi.ParsingContext

class ColladaLoaderSchema15Spec extends DocumentSpec {

    ColladaLoaderSchema15 loader
    DocumentAssembler assembler
    ColladaFragmentParser parser

    def setup() {
        assembler = Mock(DocumentAssembler)
        parser = Mock(ColladaFragmentParser)
        loader = new ColladaLoaderSchema15(assembler, parser)
    }

    def 'can load a minimalist file'() {
        setup:
        def stream = colladaFile {}
        def context = Mock(ParsingContext)
        context.getMainFileInputStream() >> stream
        context.isValidating() >> false

        when:
        def result = loader.load(context)

        then:
        result.version.majorVersion == 1
        result.version.minorVersion == 5
        result.version.thirdVersion == 0
    }
}
