package net.dryanhild.jcollada.schema14;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.schema14.geometry.GeometryParser;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;

import org.collada.x2005.x11.colladaSchema.COLLADADocument;
import org.collada.x2005.x11.colladaSchema.COLLADADocument.COLLADA;
import org.collada.x2005.x11.colladaSchema.GeometryDocument.Geometry;
import org.collada.x2005.x11.colladaSchema.LibraryGeometriesDocument.LibraryGeometries;

public class ParsingFactory {

    public ColladaScene14 parseDocument(COLLADADocument document) {
        ColladaScene14 scene = new ColladaScene14();

        COLLADA collada = document.getCOLLADA();

        for (LibraryGeometries lib : collada.getLibraryGeometriesArray()) {
            for (GeometryResult g : parseGeometries(lib)) {
                scene.getGeometries().add(g);
            }
        }

        return scene;
    }

    public Collection<GeometryResult> parseGeometries(LibraryGeometries library) {
        Collection<GeometryResult> geoms = new ArrayList<>();

        GeometryParser parser = new GeometryParser();

        for (Geometry g : library.getGeometryArray()) {
            geoms.add(parser.parse(g));
        }

        return geoms;
    }
}
