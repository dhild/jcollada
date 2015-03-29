package net.dryanhild.collada.schema15.data.geometry;

import com.carrotsearch.hppc.FloatArrayList;
import com.carrotsearch.hppc.IntArrayList;
import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.common.metadata.MutableAsset;

@Getter
@Setter
public class RawSource {

    private String id;
    private String name;
    private MutableAsset asset;

    private FloatArrayList floats;
    private IntArrayList ints;

}
