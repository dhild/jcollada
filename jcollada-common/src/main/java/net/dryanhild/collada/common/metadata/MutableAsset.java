package net.dryanhild.collada.common.metadata;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.data.metadata.Asset;

import java.time.ZonedDateTime;

@Getter
@Setter
public class MutableAsset implements Asset {

    private ZonedDateTime created;
    private ZonedDateTime modified;

    private String subject;
    private String title;
    private String revision;

}
