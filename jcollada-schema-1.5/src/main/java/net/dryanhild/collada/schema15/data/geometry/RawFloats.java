package net.dryanhild.collada.schema15.data.geometry;

import com.carrotsearch.hppc.FloatArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawFloats {

    private String id;
    private String name;
    private int count;
    private Integer digits;
    private Integer magnitude;

    private FloatArrayList floats;

}
