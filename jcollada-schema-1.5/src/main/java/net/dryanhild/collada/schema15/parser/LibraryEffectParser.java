package net.dryanhild.collada.schema14.parser;

import javax.inject.Inject;
import javax.inject.Named;

import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.schema14.ColladaDocument15;

import org.collada.x2008.x03.colladaSchema.EffectType;
import org.collada.x2008.x03.colladaSchema.LibraryEffectsType;
import org.jvnet.hk2.annotations.Service;

@Service
public class LibraryEffectParser {

    @Inject
    @Named("EffectParser")
    private Schema15Parser<EffectType, Effect> effectParser;

    public void parse(ColladaDocument15 document, LibraryEffectsType library) {
        for (EffectType effect : library.getEffectArray()) {
            document.addEffect(effectParser.parse(effect));
        }
    }
}
