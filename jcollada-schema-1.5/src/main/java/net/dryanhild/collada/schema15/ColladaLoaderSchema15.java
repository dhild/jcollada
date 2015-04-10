package net.dryanhild.collada.schema15;

import com.google.common.collect.ImmutableList;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema15.parser.ColladaFragmentParser;
import net.dryanhild.collada.schema15.postprocess.DocumentAssembler;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.collada.schema15.COLLADA;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.regex.Pattern;
import javax.inject.Inject;

@Service
public class ColladaLoaderSchema15 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_5_0 = new VersionSupport(1, 5, 0);

    private static final Pattern SCHEMA_PATTERN =
            Pattern.compile(".*COLLADA[^>]+version\\s?=\\s?\"1\\.5\\.0\".*", Pattern.DOTALL);

    private static final Logger logger = LoggerFactory.getLogger(ColladaLoaderSchema15.class);

    private final DocumentAssembler documentAssembler;
    private final ColladaFragmentParser documentParser;

    @Inject
    public ColladaLoaderSchema15(DocumentAssembler documentAssembler,ColladaFragmentParser documentParser) {
        this.documentAssembler = documentAssembler;
        this.documentParser = documentParser;
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
        try (InputStream inputStream = context.getMainFileInputStream()) {
            COLLADA fragment = documentParser.parse(context.isValidating(), inputStream);
            documentAssembler.addFragment(fragment);
            return documentAssembler.assemble();
        }
    }
}
