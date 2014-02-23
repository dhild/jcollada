package net.dryanhild.collada.schema15;

import java.io.Reader;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.Library;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

public class DAEContext {

    private final Reader reader;

    public DAEContext(Reader reader) {
        this.reader = reader;
    }

    public ColladaDocument loadDocument() {
        ColladaDocument document = new ColladaDocument() {

            @Override
            public Library<? extends Geometry> getGeometries() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Library<? extends Node> getNodes() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Library<? extends VisualScene> getVisualScenes() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public VisualScene getMainScene() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        return document;
    }

}
