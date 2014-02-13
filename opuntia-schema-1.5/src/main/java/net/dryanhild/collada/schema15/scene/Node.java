package net.dryanhild.collada.schema15.scene;

import java.util.List;

import net.dryanhild.collada.schema15.geometry.Geometry;

import com.google.common.collect.ImmutableList;

public class Node {

    public List<? extends Geometry> getGeometries() {
        return ImmutableList.of();
    }

}
