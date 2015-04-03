package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Accessor {

    private int count;
    private int offset;
    private URI source;
    private int stride;

    private final List<Param> params = new ArrayList<>();

    public void addParam(Param param) {
        params.add(param);
    }

}
