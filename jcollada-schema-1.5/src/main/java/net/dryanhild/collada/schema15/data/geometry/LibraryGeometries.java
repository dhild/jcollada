package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.common.metadata.MutableAsset;
import net.dryanhild.collada.data.geometry.Geometry;

import java.util.List;

@Getter
@Setter
public class LibraryGeometries {

    private String id;
    private String name;
    private MutableAsset asset;
    private List<Geometry> geometries;

}
