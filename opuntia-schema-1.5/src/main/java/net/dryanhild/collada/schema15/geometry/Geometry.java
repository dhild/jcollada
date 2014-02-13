package net.dryanhild.collada.schema15.geometry;

import java.util.Set;

public interface Geometry {

    Set<String> getSemantics();

    DataDescriptor getDescriptor(String semantic);

    Iterable<Vertex> getVertices();

}
