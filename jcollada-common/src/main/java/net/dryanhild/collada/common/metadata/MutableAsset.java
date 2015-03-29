package net.dryanhild.collada.common.metadata;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.data.metadata.Asset;

import java.time.Instant;

@Getter
@Setter
public class MutableAsset implements Asset {

    private Instant created;
    private Instant modified;

    private String subject;
    private String title;
    private String revision;

}
