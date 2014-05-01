package net.dryanhild.collada;

import java.util.Collection;

import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;

import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

public class ColladaLoaderServiceImpl implements ColladaLoaderService {

    public static final VersionSupport TEST_VERSION = new VersionSupport(0, 0, 0);

    public static final String TEST_BASIC_FILE = "This is a test file for 0.0.0.";

    public static ParsingContext lastContext = null;

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(TEST_VERSION);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        return header.toString().contains(TEST_BASIC_FILE);
    }

    @Override
    public ColladaDocument load(ParsingContext context) {
        lastContext = context;
        return Mockito.mock(ColladaDocument.class);
    }

}
