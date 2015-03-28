package net.dryanhild.collada.schema15;

import com.google.inject.AbstractModule;
import net.dryanhild.collada.schema15.parser.ColladaFragmentParser;
import net.dryanhild.collada.schema15.postprocess.ColladaDocumentAssembler;

public class ColladaModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ColladaFragmentParser.class);
        bind(ColladaDocumentAssembler.class);
    }
}
