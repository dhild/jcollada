package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RawVertices {

    private String id;
    private String name;

    private final List<UnsharedInput> inputs = new ArrayList<>();

    public void addInput(UnsharedInput input) {
        inputs.add(input);
    }

}
