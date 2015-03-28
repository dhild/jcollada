package net.dryanhild.collada.schema15.parser;

import dagger.Module;
import dagger.Provides;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;

@Module(library = true, injects = {
        AssetParser.class,
        ColladaFragmentParser.class
})
public class ParserModule {

    @Provides
    AssetParser assetParser() {
        return new AssetParser();
    }

    @Provides
    ColladaFragmentParser colladaFragmentParser(AssetParser assetParser) {
        return new ColladaFragmentParser(assetParser);
    }

}
