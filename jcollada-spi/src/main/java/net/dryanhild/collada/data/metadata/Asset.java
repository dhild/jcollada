package net.dryanhild.collada.data.metadata;

import java.time.ZonedDateTime;

public interface Asset {

    ZonedDateTime getCreated();

    ZonedDateTime getModified();

    String getSubject();
    String getTitle();
    String getRevision();

}
