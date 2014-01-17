package net.dryanhild.jcollada.schema15;

import java.util.Collection;

import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ServiceMetadataTests {

    private ColladaLoaderService loader;

    @BeforeMethod
    public void refreshLoader() {
        loader = new Schema15Loader();
    }

    @Test
    public void versionSupportIsCorrect() {
        Collection<Version> versions = loader.getColladaVersions();

        assert versions.size() == 1;

        Version version = versions.iterator().next();

        assert version.majorVersion == 1;
        assert version.minorVersion == 5;
        assert version.thirdVersion == 0;
        assert "1.5.0".equals(version.versionString);
    }
}
