package net.dryanhild.collada.schema15.data.geometry;

import com.carrotsearch.hppc.IntArrayList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RawTriangles {

    private String name;
    private int count;
    private String material;

    private final List<SharedInput> inputs = new ArrayList<>();

    private IntArrayList p;

    public void addInput(SharedInput input) {
        inputs.add(input);
    }

}
