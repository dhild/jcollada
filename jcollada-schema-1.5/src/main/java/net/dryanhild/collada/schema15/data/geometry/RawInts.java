package net.dryanhild.collada.schema15.data.geometry;

import com.carrotsearch.hppc.IntArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawInts {

    private String id;
    private String name;
    private int count;
    private Integer minInclusive;
    private Integer maxInclusive;

    private IntArrayList ints;

}
