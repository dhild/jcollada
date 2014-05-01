package net.dryanhild.collada.spi;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface ColladaLoaderService {

    Collection<VersionSupport> getColladaVersions();

    boolean canLoad(@NotNull CharSequence header);

    ColladaDocument load(@NotNull ParsingContext context);

}
