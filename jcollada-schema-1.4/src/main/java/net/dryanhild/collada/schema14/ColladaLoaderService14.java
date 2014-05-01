package net.dryanhild.collada.schema14;

import com.google.common.collect.ImmutableList;
import net.dryanhild.collada.ParsingException;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema14.parser.DocumentParser;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.regex.Pattern;

public class ColladaLoaderService14 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_4_0 = new VersionSupport(1, 4, 0);
    public static final VersionSupport VERSION_1_4_1 = new VersionSupport(1, 4, 1);

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            ".*COLLADA[^>]+version\\s?=\\s?\\\"1\\.4\\.[01]\\\".*", Pattern.DOTALL);

    private DocumentParser documentParser = new DocumentParser();

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(VERSION_1_4_0, VERSION_1_4_1);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        return VERSION_PATTERN.matcher(header).matches();
    }

    @Override
    public ColladaDocument load(ParsingContext context) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(context.isValidating());
            XmlPullParser parser = factory.newPullParser();
            Reader reader = new InputStreamReader(context.getMainFileInputStream(), context.getCharset());
            parser.setInput(reader);

            return documentParser.parse(parser);
        } catch (XmlPullParserException e) {
            throw new ParsingException("Unable to parse document!", e);
        }
    }
}
