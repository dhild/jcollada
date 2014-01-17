package net.dryanhild.jcollada.spi;

import java.util.Collection;

import net.dryanhild.jcollada.metadata.Version;

import com.sun.j3d.loaders.Scene;

public interface ColladaLoaderService {

    Collection<Version> getColladaVersions();

    boolean canLoad(CharSequence header);

    Scene load(ParsingContext context);

}
