package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.common.metadata.MutableAsset;

@Getter
@Setter
public class RawGeometry {

    private String id;
    private String name;
    private MutableAsset asset;
    private RawMesh mesh;

}
