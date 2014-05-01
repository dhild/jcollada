package net.dryanhild.collada.schema15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.hk2spi.ColladaLoader;
import net.dryanhild.collada.hk2spi.ParsingContext;
import net.dryanhild.collada.schema15.parser.DAEParser;

import org.jvnet.hk2.annotations.Service;

import com.google.common.collect.ImmutableList;

@Service
@Schema15
public class ColladaLoaderSchema15 implements ColladaLoader {

    public static final VersionSupport VERSION_1_5_0 = new VersionSupport(1, 5, 0);

    private static final Pattern SCHEMA_PATTERN = Pattern.compile(".*COLLADA[^>]+version\\s?=\\s?\\\"1\\.5\\.0\\\".*",
            Pattern.DOTALL);

    @Inject
    private DAEParser parser;

    @Override
    public Iterable<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_5_0);
    }

    @Override
    public boolean canLoad(ParsingContext context) {
        byte[] header = context.getMainFileHeader();
        String headerString = new String(header);
        Matcher matcher = SCHEMA_PATTERN.matcher(headerString);
        return matcher.matches();
    }

    @Override
    public ColladaDocument load(ParsingContext context) {
        return parser.parse(context.getMainFileInputStream());
    }
}
