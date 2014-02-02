package net.dryanhild.jcollada.schema14;

import java.util.Collection;
import java.util.regex.Pattern;

import net.dryanhild.jcollada.VersionSupport;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ParsingContext;

import com.google.common.collect.ImmutableList;

public class ColladaLoaderService14 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_4_0 = new VersionSupport(1, 4, 0);
    public static final VersionSupport VERSION_1_4_1 = new VersionSupport(1, 4, 1);

    private static final Pattern VERSION_PATTERN = Pattern
            .compile(".*COLLADA[^>]+version\\s?=\\s?\\\"1\\.4\\.[01]\\\".*");

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_4_0, VERSION_1_4_1);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        return VERSION_PATTERN.matcher(header).matches();
    }

    @Override
    public ColladaScene load(ParsingContext context) {
        // TODO Auto-generated method stub
        return null;
    }

}
