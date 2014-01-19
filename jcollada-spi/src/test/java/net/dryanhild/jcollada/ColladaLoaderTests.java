package net.dryanhild.jcollada;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;

import net.dryanhild.jcollada.metadata.Version;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.LoaderBase;

public class ColladaLoaderTests {

    @Test
    public void serviceProviderImplementationFound() {
        ColladaLoader loader = new ColladaLoader();

        Collection<Version> versions = loader.getRegisteredVersions();

        assert versions.contains(ColladaLoaderServiceImpl.TEST_VERSION);
    }

    @DataProvider
    public Object[][] flagConfigurations() {
        return new Object[][] { //
        //
                { false, 0 }, //
                { false, LoaderBase.LOAD_ALL }, //
                { true, 0 }, //
                { true, LoaderBase.LOAD_ALL }, //
        };
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadReader(boolean validate, int flags) {
        ColladaLoader loader = createLoader(validate, flags);

        Reader reader = new StringReader(ColladaLoaderServiceImpl.TEST_BASIC_FILE);
        loader.load(reader);

        checkLoadContext(validate, flags);
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadFile(boolean validate, int flags) throws IOException {
        ColladaLoader loader = createLoader(validate, flags);

        File tempFile = writeTestStringToTempFile();

        loader.load(tempFile.getAbsolutePath());

        checkLoadContext(validate, flags);
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadURL(boolean validate, int flags) throws IOException {
        ColladaLoader loader = createLoader(validate, flags);

        File tempFile = writeTestStringToTempFile();

        loader.load(tempFile.toURI().toURL());

        checkLoadContext(validate, flags);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void loadBadFile() throws IOException {
        ColladaLoader loader = new ColladaLoader();

        loader.load("non existant file");
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void loadBadUrl() throws IOException {
        ColladaLoader loader = new ColladaLoader();

        loader.load(new URL("file:///non existant file"));
    }

    @Test(expectedExceptions = IncorrectFormatException.class)
    public void loadNonImplementedLoader() {
        ColladaLoader loader = new ColladaLoader();

        Reader reader = new StringReader("Bad input.");
        loader.load(reader);
    }

    private ColladaLoader createLoader(boolean validate, int flags) {
        ColladaLoader loader = new ColladaLoader();
        loader.setValidating(validate);
        loader.setFlags(flags);
        return loader;
    }

    private void checkLoadContext(boolean validate, int flags) {
        assert ColladaLoaderServiceImpl.lastContext.isValidating() == validate;
        assert ColladaLoaderServiceImpl.lastContext.getFlags() == flags;
        assert ColladaLoaderServiceImpl.lastContext.getMainFileReader() != null;
    }

    private File writeTestStringToTempFile() throws IOException {
        File tempFile = File.createTempFile("collada-loader-temp", ".file");
        tempFile.deleteOnExit();
        Writer output = new OutputStreamWriter(new FileOutputStream(tempFile));
        output.write(ColladaLoaderServiceImpl.TEST_BASIC_FILE);
        output.flush();
        output.close();
        return tempFile;
    }
}
