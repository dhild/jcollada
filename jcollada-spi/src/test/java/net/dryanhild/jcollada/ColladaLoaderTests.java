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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ColladaLoaderTests {

    @Test
    public void serviceProviderImplementationFound() {
        ColladaLoader loader = new ColladaLoader();

        Collection<VersionSupport> versions = loader.getRegisteredVersions();

        assert versions.contains(ColladaLoaderServiceImpl.TEST_VERSION);
    }

    @DataProvider
    public Object[][] flagConfigurations() {
        return new Object[][] { //
        //
                { false }, //
                { true }, //
        };
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadReader(boolean validate) {
        ColladaLoader loader = createLoader(validate);

        Reader reader = new StringReader(ColladaLoaderServiceImpl.TEST_BASIC_FILE);
        loader.load(reader);

        checkLoadContext(validate);
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadFile(boolean validate) throws IOException {
        ColladaLoader loader = createLoader(validate);

        File tempFile = writeTestStringToTempFile();

        loader.load(tempFile.getAbsolutePath());

        checkLoadContext(validate);
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadURL(boolean validate) throws IOException {
        ColladaLoader loader = createLoader(validate);

        File tempFile = writeTestStringToTempFile();

        loader.load(tempFile.toURI().toURL());

        checkLoadContext(validate);
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

    private ColladaLoader createLoader(boolean validate) {
        ColladaLoader loader = new ColladaLoader();
        loader.setValidating(validate);
        return loader;
    }

    private void checkLoadContext(boolean validate) {
        assert ColladaLoaderServiceImpl.lastContext.isValidating() == validate;
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
