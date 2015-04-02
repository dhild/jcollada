package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RawMesh {

    private final List<RawSource> sources = new ArrayList<>();

    private RawVertices vertices;

    private final List<RawPolygons> polygons = new ArrayList<>();
    private final List<RawTriangles> triangles = new ArrayList<>();

}
