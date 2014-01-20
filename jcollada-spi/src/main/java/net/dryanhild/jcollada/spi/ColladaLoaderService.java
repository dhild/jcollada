package net.dryanhild.jcollada.spi;

import java.util.Collection;

import net.dryanhild.jcollada.VersionSupport;
import net.dryanhild.jcollada.data.ColladaScene;

public interface ColladaLoaderService {

    Collection<VersionSupport> getColladaVersions();

    boolean canLoad(CharSequence header);

    ColladaScene load(ParsingContext context);

}
