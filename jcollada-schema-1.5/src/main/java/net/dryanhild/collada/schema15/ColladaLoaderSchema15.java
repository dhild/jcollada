package net.dryanhild.collada.schema15;

import com.google.common.collect.ImmutableList;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema15.data.Collada15Document;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

public class ColladaLoaderSchema15 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_5_0 = new VersionSupport(1, 5, 0);

    private static final Pattern SCHEMA_PATTERN =
            Pattern.compile(".*COLLADA[^>]+version\\s?=\\s?\"1\\.5\\.0\".*", Pattern.DOTALL);

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_5_0);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        return SCHEMA_PATTERN.matcher(header).matches();
    }

    @Override
    public ColladaDocument load(ParsingContext context) throws IOException {
        return new Collada15Document();
    }
}
