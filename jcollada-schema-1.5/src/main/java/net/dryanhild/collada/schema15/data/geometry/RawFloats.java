package net.dryanhild.collada.schema15.data.geometry;

import com.carrotsearch.hppc.FloatArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawFloats {

    private String id;
    private String name;
    private long count;
    private int digits;
    private int magnitude;

    private FloatArrayList floats;

}
