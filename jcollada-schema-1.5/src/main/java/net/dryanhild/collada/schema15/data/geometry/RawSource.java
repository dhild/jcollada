package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.common.metadata.MutableAsset;

@Getter
@Setter
public class RawSource {

    private String id;
    private String name;
    private MutableAsset asset;

    private RawFloats floats;
    private RawInts ints;

    private SourceTechniqueCommon techniqueCommon;

}
