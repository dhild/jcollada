package net.dryanhild.jcollada.schema14.geometry;

import org.collada.x2005.x11.colladaSchema.PolylistDocument.Polylist;
import org.collada.x2005.x11.colladaSchema.VerticesDocument.Vertices;

public class PolylistParser {

    private final Vertices vertices;
    private final Polylist polys;

    public PolylistParser(Polylist polyList, Vertices vertices) {
        polys = polyList;
        this.vertices = vertices;
    }

}
