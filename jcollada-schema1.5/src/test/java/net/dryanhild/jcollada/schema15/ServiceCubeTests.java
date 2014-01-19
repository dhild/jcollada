package net.dryanhild.jcollada.schema15;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.testng.annotations.Test;

import com.sun.j3d.loaders.Scene;

public class ServiceCubeTests {

    @Test(groups = "integrationTests")
    public void versionCanBeLoadedRealFile() {
        ColladaLoaderService service = new Schema15Loader();

        String fileHeader = BasicCubeDefinition.getCubeContent();

        assert service.canLoad(fileHeader);
    }

    @Test(groups = "integrationTests")
    public void loadOnRealFileWorks() throws FileNotFoundException {
        ColladaLoader loader = new ColladaLoader();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Reader reader =
                new InputStreamReader(new BufferedInputStream(
                        classLoader.getResourceAsStream(BasicCubeDefinition.TEST_FILE_LOCATION)));

        Scene scene = loader.load(reader);

        assert scene != null;
    }

}
