package net.dryanhild.collada.schema15.parser;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = {
        ColladaFragmentParser.class
})
public class ParserModule {

    @Provides
    ColladaFragmentParser colladaFragmentParser() {
        return new ColladaFragmentParser();
    }

}
