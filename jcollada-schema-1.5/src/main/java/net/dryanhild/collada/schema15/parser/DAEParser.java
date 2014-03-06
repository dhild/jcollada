package net.dryanhild.collada.schema15.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import net.dryanhild.collada.ParsingException;
import net.dryanhild.collada.schema15.ColladaDocument15;

import org.apache.xmlbeans.XmlException;
import org.collada.x2008.x03.colladaSchema.COLLADADocument;
import org.collada.x2008.x03.colladaSchema.COLLADADocument.COLLADA;
import org.collada.x2008.x03.colladaSchema.LibraryEffectsType;
import org.jvnet.hk2.annotations.Service;

@Service
@Named
public class DAEParser {

    @Inject
    private LibraryEffectParser libraryEffectParser;

    public ColladaDocument15 parse(InputStream input) {
        try {
            COLLADADocument documentImpl = COLLADADocument.Factory.parse(input);

            COLLADA collada = documentImpl.getCOLLADA();

            return parse(collada);
        } catch (XmlException | IOException e) {
            throw new ParsingException("Error while loading file!", e);
        }
    }

    private ColladaDocument15 parse(COLLADA collada) {
        ColladaDocument15 document = new ColladaDocument15();

        parseEffects(document, collada.getLibraryEffectsArray());

        return document;
    }

    private void parseEffects(ColladaDocument15 document, LibraryEffectsType[] effects) {
        for (LibraryEffectsType effect : effects) {
            libraryEffectParser.parse(document, effect);
        }
    }

}
