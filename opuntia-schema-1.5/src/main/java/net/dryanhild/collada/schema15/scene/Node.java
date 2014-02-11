package net.dryanhild.collada.schema15.scene;

import java.util.List;

import net.dryanhild.collada.schema15.geometry.GeometryMesh;

import com.google.common.collect.ImmutableList;

public class Node {

    public List<? extends GeometryMesh> getGeometries() {
        return ImmutableList.of();
    }

}
