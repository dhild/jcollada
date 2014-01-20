package net.dryanhild.jcollada;

import java.util.Collection;

import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ParsingContext;

import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

public class ColladaLoaderServiceImpl implements ColladaLoaderService {

    public static final VersionSupport TEST_VERSION = new VersionSupport(0, 0, 0, "test-version");

    public static final String TEST_BASIC_FILE = "This is a test file for 0.0.0, test-version.";

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
    public ColladaScene load(ParsingContext context) {
        lastContext = context;
        return Mockito.mock(ColladaScene.class);
    }

}
