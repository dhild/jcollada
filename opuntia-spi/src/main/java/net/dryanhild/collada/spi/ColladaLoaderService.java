package net.dryanhild.collada.spi;

import java.util.Collection;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaScene;

public interface ColladaLoaderService {

    Collection<VersionSupport> getColladaVersions();

    boolean canLoad(CharSequence header);

    ColladaScene load(ParsingContext context);

}
