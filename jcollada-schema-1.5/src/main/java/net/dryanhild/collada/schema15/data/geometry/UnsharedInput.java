package net.dryanhild.collada.schema15.data.geometry;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.common.annotations.NameToken;

import java.net.URI;

@Getter
@Setter
public class UnsharedInput {

    @NameToken
    private String semantic;
    private URI source;

}
