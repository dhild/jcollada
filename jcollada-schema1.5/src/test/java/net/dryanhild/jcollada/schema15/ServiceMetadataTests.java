package net.dryanhild.jcollada.schema15;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.testng.annotations.Test;

import com.sun.j3d.loaders.Scene;

public class ServiceMetadataTests {

    @Test
    public void versionSupportIsCorrect() {
        ColladaLoaderService loader = new Schema15Loader();
        Collection<Version> versions = loader.getColladaVersions();

        assert versions.size() == 1;

        Version version = versions.iterator().next();

        assert version.majorVersion == 1;
        assert version.minorVersion == 5;
        assert version.thirdVersion == 0;
        assert "1.5.0".equals(version.versionString);
    }

    @Test
    public void versionIsIdentifiedByService() {
        ColladaLoader loader = new ColladaLoader();

        Collection<Version> versions = loader.getRegisteredVersions();

        assert versions.contains(new Version(1, 5, 0, "1.5.0"));
    }

    @Test
    public void versionCanBeLoaded() {
        ColladaLoaderService service = new Schema15Loader();

        String fileHeader = "<COLLADA version=\"1.5.0\">";

        assert service.canLoad(fileHeader);
    }

    @Test(groups = "integrationTests")
    public void versionCanBeLoadedRealFile() {
        ColladaLoaderService service = new Schema15Loader();

        String fileHeader = BasicCubeDefinition.getCubeContent();

        assert service.canLoad(fileHeader);
    }

    @Test(groups = "integrationTests")
    public void loadOnRealFileWorks() {
        ColladaLoader loader = new ColladaLoader();

        Reader reader = new StringReader(BasicCubeDefinition.getCubeContent());

        Scene scene = loader.load(reader);

        assert scene != null;
    }
}
