package net.dryanhild.collada.schema15.data.geometry;

import com.carrotsearch.hppc.IntArrayList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RawPolygons {

    private int count;
    private String material;
    private String name;

    private final List<SharedInput> inputs = new ArrayList<>();
    private final List<IntArrayList> p = new ArrayList<>();
    private final List<IntArrayList> ph = new ArrayList<>();

}
