package net.dryanhild.collada.schema15;

import java.net.URI;
import java.util.Collection;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaScene;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;

import com.google.common.collect.ImmutableList;

public class ColladaLoaderSchema15 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_5_0 = new VersionSupport(1, 5, 0);

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_5_0);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ColladaScene load(ParsingContext context) {
        // TODO Auto-generated method stub
        return null;
    }

    public ColladaScene load(URI input) {
        return null;
    }

}
