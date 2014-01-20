package net.dryanhild.jcollada.schema15;

import java.util.Collection;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.VersionSupport;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.testng.annotations.Test;

public class ServiceMetadataTests {

    @Test
    public void versionSupportIsCorrect() {
        ColladaLoaderService loader = new Schema15Loader();
        Collection<VersionSupport> versions = loader.getColladaVersions();

        assert versions.size() == 1;

        VersionSupport version = versions.iterator().next();

        assert version.majorVersion == 1;
        assert version.minorVersion == 5;
        assert version.thirdVersion == 0;
        assert "1.5.0".equals(version.versionString);
    }

    @Test
    public void versionIsIdentifiedByService() {
        ColladaLoader loader = new ColladaLoader();

        Collection<VersionSupport> versions = loader.getRegisteredVersions();

        assert versions.contains(new VersionSupport(1, 5, 0, "1.5.0"));
    }

    @Test
    public void versionCanBeLoaded() {
        ColladaLoaderService service = new Schema15Loader();

        String fileHeader = "<COLLADA version=\"1.5.0\">";

        assert service.canLoad(fileHeader);
    }
}
