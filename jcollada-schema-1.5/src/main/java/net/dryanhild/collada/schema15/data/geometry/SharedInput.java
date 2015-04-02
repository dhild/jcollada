package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class SharedInput {

    private int offset;
    private String semantic;
    private URI source;
    private int set;

}
