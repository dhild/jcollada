package net.dryanhild.collada.data.metadata;

import java.time.Instant;

public interface Asset {

    Instant getCreated();

    Instant getModified();

    String getSubject();
    String getTitle();
    String getRevision();

}
