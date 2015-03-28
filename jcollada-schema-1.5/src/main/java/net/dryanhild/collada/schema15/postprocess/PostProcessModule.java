package net.dryanhild.collada.schema15.postprocess;

import dagger.Module;
import dagger.Provides;

@Module(library = true, injects = {
        ColladaDocumentAssembler.class
})
public class PostProcessModule {

    @Provides
    ColladaDocumentAssembler documentAssembler() {
        return new ColladaDocumentAssembler();
    }

}
