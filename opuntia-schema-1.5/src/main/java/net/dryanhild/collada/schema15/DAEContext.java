package net.dryanhild.collada.schema15;

import java.io.Reader;

public class DAEContext {

    private final Reader reader;

    public DAEContext(Reader reader) {
        this.reader = reader;
    }

    public ColladaDocument loadDocument() {
        ColladaDocument document = new ColladaDocument();

        return document;
    }

}
