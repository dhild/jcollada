package net.dryanhild.collada.schema15;

import com.google.inject.AbstractModule;
import net.dryanhild.collada.schema15.parser.ColladaDocumentParser;

public class ColladaModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ColladaDocumentParser.class);
    }
}
