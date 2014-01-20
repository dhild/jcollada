package net.dryanhild.jcollada.schema15;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.data.AssetDescription;
import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import com.sun.j3d.loaders.Scene;

@Test(groups = "integrationTests")
public class ServiceCubeTests {

    public void versionCanBeLoadedRealFile() {
        ColladaLoaderService service = new Schema15Loader();

        String fileHeader = BasicCubeDefinition.getCubeContent();

        assert service.canLoad(fileHeader);
    }

    public void loadOnRealFileWorks() throws FileNotFoundException {
        Scene scene = loadCubeScene();

        assert scene != null;
    }

    private ColladaScene loadCubeScene() {
        ColladaLoader loader = new ColladaLoader();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Reader reader =
                new InputStreamReader(new BufferedInputStream(
                        classLoader.getResourceAsStream(BasicCubeDefinition.TEST_FILE_LOCATION)));

        return loader.load(reader);
    }

    public void loadTestingMainAsset() {
        ColladaScene scene = loadCubeScene();

        AssetDescription mainAsset = scene.getMainAsset();

        DateTime created = new DateTime(2005, 11, 14, 02, 16, 38, DateTimeZone.UTC);
        DateTime modified = new DateTime(2005, 11, 15, 11, 36, 38, DateTimeZone.UTC);

        String revision = "1.0";

        assert created.getMillis() == mainAsset.getCreated().getMillis();
        assert modified.getMillis() == mainAsset.getModified().getMillis();
        assert revision.equals(mainAsset.getRevision());
    }
}
