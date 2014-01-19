package net.dryanhild.jcollada;

import java.util.Collection;

import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ParsingContext;

import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;
import com.sun.j3d.loaders.Scene;

public class ColladaLoaderServiceImpl implements ColladaLoaderService {

    public static final Version TEST_VERSION = new Version(0, 0, 0, "test-version");

    public static final String TEST_BASIC_FILE = "This is a test file for 0.0.0, test-version.";

    public static ParsingContext lastContext = null;

    @Override
    public Collection<Version> getColladaVersions() {
        return ImmutableList.of(TEST_VERSION);
    }

    @Override
    public boolean canLoad(CharSequence header) {
        return header.toString().contains(TEST_BASIC_FILE);
    }

    @Override
    public Scene load(ParsingContext context) {
        lastContext = context;
        return Mockito.mock(Scene.class);
    }

}
