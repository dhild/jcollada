package net.dryanhild.collada;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import net.dryanhild.collada.ColladaLoader;
import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.ParsingException;
import net.dryanhild.collada.VersionSupport;

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
                { Boolean.FALSE }, //
                { Boolean.TRUE }, //
        };
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadReader(boolean validate) {
        ColladaLoader loader = createLoader(validate);

        Reader reader = new StringReader(ColladaLoaderServiceImpl.TEST_BASIC_FILE);
        loader.load(reader);

        checkLoadContext(validate);
    }

    @Test(expectedExceptions = IncorrectFormatException.class)
    public void loadNonImplementedLoader() {
        ColladaLoader loader = new ColladaLoader();

        Reader reader = new StringReader("Bad input.");
        loader.load(reader);
    }

    @Test
    public void ioExceptionInHeaderRead() throws IOException {
        Reader reader = new ErrorOnReadReader();
        ColladaLoader loader = new ColladaLoader();
        try {
            loader.load(reader);

            assert false : "Expected an IOException to be thrown!";
        } catch (ParsingException e) {
            assertThat(e).hasMessageContaining("while reading");
            assertThat(e).hasCauseInstanceOf(IOException.class);
        }
    }

    @Test
    public void ioExceptionInClose() throws IOException {
        Reader reader = new ErrorOnCloseReader();
        ColladaLoader loader = new ColladaLoader();
        try {
            loader.load(reader);

            assert false : "Expected an IOException to be thrown!";
        } catch (ParsingException e) {
            assertThat(e).hasMessageContaining("while closing");
            assertThat(e).hasCauseInstanceOf(IOException.class);
        }
    }

    private class ErrorOnReadReader extends Reader {
        @Override
        public void close() throws IOException {
            // Doesn't matter here.
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            throw new IOException();
        }
    }

    private class ErrorOnCloseReader extends Reader {
        private final StringReader reader = new StringReader(ColladaLoaderServiceImpl.TEST_BASIC_FILE);

        @Override
        public void close() throws IOException {
            throw new IOException();
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return reader.read(cbuf, off, len);
        }

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
}
