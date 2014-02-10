package net.dryanhild.collada.schema14;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.ParsingException;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaScene;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;

import org.apache.xmlbeans.XmlException;
import org.collada.x2005.x11.colladaSchema.COLLADADocument;

import com.google.common.collect.ImmutableList;

public class ColladaLoaderService14 implements ColladaLoaderService {

    public static final VersionSupport VERSION_1_4_0 = new VersionSupport(1, 4, 0);
    public static final VersionSupport VERSION_1_4_1 = new VersionSupport(1, 4, 1);

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            ".*COLLADA[^>]+version\\s?=\\s?\\\"1\\.4\\.[01]\\\".*", Pattern.DOTALL);

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
        COLLADADocument document = readMainDocument(context);

        ParsingFactory parser = new ParsingFactory();
        parser.parseDocument(document);

        return parser.getScene();
    }

    private COLLADADocument readMainDocument(ParsingContext context) {
        try {
            return COLLADADocument.Factory.parse(context.getMainFileReader());
        } catch (IOException e) {
            throw new ParsingException("Unable to read the document!", e);
        } catch (XmlException e) {
            throw new IncorrectFormatException("Unable to parse the document!", e);
        }
    }
}
