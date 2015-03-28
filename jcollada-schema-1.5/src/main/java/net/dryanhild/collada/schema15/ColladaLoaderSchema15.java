package net.dryanhild.collada.schema15;

import com.google.common.collect.ImmutableList;
import dagger.ObjectGraph;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema15.data.ColladaDocumentFragment;
import net.dryanhild.collada.schema15.parser.ColladaFragmentParser;
import net.dryanhild.collada.schema15.postprocess.ColladaDocumentAssembler;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.regex.Pattern;

public class ColladaLoaderSchema15 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_5_0 = new VersionSupport(1, 5, 0);

    private static final Pattern SCHEMA_PATTERN =
            Pattern.compile(".*COLLADA[^>]+version\\s?=\\s?\"1\\.5\\.0\".*", Pattern.DOTALL);

    private static final Logger logger = LoggerFactory.getLogger(ColladaLoaderSchema15.class);

    private ObjectGraph objectGraph;

    public ColladaLoaderSchema15() {
        objectGraph = ObjectGraph.create(new ColladaModule());
    }

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_5_0);
    }

    @Override
    public boolean canLoad(ParsingContext context) throws IOException {
        if (context.getSourceUri().getPath().endsWith(".zae")) {
            logger.debug("Unable to load .zae files (yet)");
            return false;
        }
        return SCHEMA_PATTERN.matcher(context.getMainFileHeader()).matches();
    }

    @Override
    public ColladaDocument load(ParsingContext context) throws IOException {
        ColladaDocumentAssembler assembler = objectGraph.get(ColladaDocumentAssembler.class);

        assembler.addFragment(parseFragment(context, context.getSourceUri(), context.getMainFileInputStream()));

        return assembler.assemble();
    }

    private ColladaDocumentFragment parseFragment(ParsingContext context, URI uri, InputStream inputStream)
            throws IOException {
        XmlPullParser parser = XmlParser.createPullParser(context.isValidating(), inputStream, context.getCharset());
        ColladaFragmentParser documentParser = objectGraph.get(ColladaFragmentParser.class);
        return documentParser.parse(uri, parser);
    }
}
