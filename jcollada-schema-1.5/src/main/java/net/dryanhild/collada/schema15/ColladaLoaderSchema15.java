package net.dryanhild.collada.schema15;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema15.parser.ColladaDocumentParser;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

public class ColladaLoaderSchema15 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_5_0 = new VersionSupport(1, 5, 0);

    private static final Pattern SCHEMA_PATTERN =
            Pattern.compile(".*COLLADA[^>]+version\\s?=\\s?\"1\\.5\\.0\".*", Pattern.DOTALL);

    private Injector injector;

    public ColladaLoaderSchema15() {
        injector = Guice.createInjector(new ColladaModule());
    }

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_5_0);
    }

    @Override
    public boolean canLoad(ParsingContext context) throws IOException {
        return SCHEMA_PATTERN.matcher(context.getMainFileHeader()).matches();
    }

    @Override
    public ColladaDocument load(ParsingContext context) throws IOException {
        XmlPullParser parser = XmlParser.createPullParser(context);

        ColladaDocumentParser documentParser = injector.getInstance(ColladaDocumentParser.class);

        return documentParser.parse(parser);
    }
}
